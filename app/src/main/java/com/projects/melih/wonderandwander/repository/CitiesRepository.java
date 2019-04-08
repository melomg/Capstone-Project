package com.projects.melih.wonderandwander.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Constants;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.local.LocalCityDataSource;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.WonderAndWanderService;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Melih Gültekin on 21.06.2018
 */
@Singleton
public class CitiesRepository {
    private static final int LIMIT = 1;
    private static final int CITY_STORAGE_LIMIT = 10;
    private static final String EMBED_URL_SCORES = "city:search-results/city:item/city:urban_area/ua:scores";
    private static final String EMBED_URL_IMAGES = "city:search-results/city:item/city:urban_area/ua:images";
    private final Lazy<Context> lazyContext;
    private final Lazy<LocalCityDataSource> lazyLocalCityDataSource;
    private final Lazy<WonderAndWanderService> lazyService;
    private final Lazy<AppExecutors> lazyAppExecutors;
    private LiveData<List<City>> lastSearchedCitiesLiveData;
    @NonNull
    private final List<City> comparedCities = new ArrayList<>();

    @Inject
    CitiesRepository(@NonNull final Lazy<Context> applicationContext,
                     @NonNull final Lazy<LocalCityDataSource> localCityDataSource,
                     @NonNull final Lazy<WonderAndWanderService> service,
                     @NonNull final Lazy<AppExecutors> appExecutors) {
        this.lazyContext = applicationContext;
        this.lazyLocalCityDataSource = localCityDataSource;
        this.lazyService = service;
        this.lazyAppExecutors = appExecutors;
    }

    public LiveData<List<City>> getLastSearchedCitiesLiveData() {
        if (lastSearchedCitiesLiveData == null) {
            lastSearchedCitiesLiveData = lazyLocalCityDataSource.get().getLastSearchedCities();
        }
        return lastSearchedCitiesLiveData;
    }

    @Nullable
    public Call<ResponseCities> fetchCitiesFromNetwork(@NonNull final String cityName, @NonNull final DataCallback<List<City>> callback) {
        Call<ResponseCities> call = null;
        if (!Utils.isNetworkConnected(lazyContext.get())) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            call = lazyService.get().getCity(cityName, LIMIT, EMBED_URL_SCORES, EMBED_URL_IMAGES);
            call.enqueue(new ResponseCitiesCallback(callback));
        }
        return call;
    }

    public void removeLastSearchedCities() {
        lazyAppExecutors.get().diskIO().execute(lazyLocalCityDataSource.get()::deleteAll);
    }

    @NonNull
    public List<City> getComparedCities() {
        return Collections.unmodifiableList(comparedCities);
    }

    public boolean isCompareListContains(@Nullable final City city) {
        if (city != null) {
            for (final City comparedCity : comparedCities) {
                if (TextUtils.equals(comparedCity.getGeoHash(), city.getGeoHash())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToCompareList(@NonNull final City city) {
        if (CollectionUtils.size(comparedCities) == Constants.COMPARE_CAPACITY) {
            return false;
        }
        for (final City comparedCity : comparedCities) {
            if (TextUtils.equals(comparedCity.getGeoHash(), city.getGeoHash())) {
                return true;
            }
        }
        comparedCities.add(city);
        LocalBroadcastManager.getInstance(lazyContext.get()).sendBroadcast(new Intent(Constants.ACTION_COMPARE));
        return true;
    }

    public void clearCompareList() {
        comparedCities.clear();
        LocalBroadcastManager.getInstance(lazyContext.get()).sendBroadcast(new Intent(Constants.ACTION_COMPARE));
    }

    private class ResponseCitiesCallback implements Callback<ResponseCities> {
        private final DataCallback<List<City>> callback;

        ResponseCitiesCallback(final DataCallback<List<City>> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(@NonNull final Call<ResponseCities> call, @NonNull final Response<ResponseCities> response) {
            final List<City> cities = Utils.createCityListFromResponse(response);
            if (CollectionUtils.isNotEmpty(cities)) {
                callback.onComplete(cities, ErrorState.NO_ERROR);

                addToLastSearchedCities(cities);
            } else {
                callback.onComplete(null, ErrorState.EMPTY);
            }
        }

        @Override
        public void onFailure(@NonNull final Call<ResponseCities> call, @NonNull final Throwable t) {
            callback.onComplete(null, ErrorState.FAILED);
        }

        private void addToLastSearchedCities(final List<City> cities) {
            lazyAppExecutors.get().diskIO().execute(() -> {
                final LocalCityDataSource localCityDataSource = lazyLocalCityDataSource.get();
                final int numberOfRows = localCityDataSource.getNumberOfRows();
                for (final City city : cities) {
                    city.setTimeSpan(Calendar.getInstance(Constants.TIME_ZONE_UTC).getTimeInMillis());
                    if (numberOfRows == CITY_STORAGE_LIMIT) {
                        localCityDataSource.deleteFirstCity();
                    }
                    localCityDataSource.insertCity(city);
                }
            });
        }
    }
}