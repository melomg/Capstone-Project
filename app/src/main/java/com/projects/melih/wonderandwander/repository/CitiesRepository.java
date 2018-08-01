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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    private final Context context;
    private final LocalCityDataSource localCityDataSource;
    private final WonderAndWanderService service;
    private final AppExecutors appExecutors;
    private LiveData<List<City>> lastSearchedCitiesLiveData;
    @NonNull
    private List<City> comparedCities = new ArrayList<>();

    @Inject
    CitiesRepository(@NonNull Context applicationContext, @NonNull LocalCityDataSource localCityDataSource, @NonNull WonderAndWanderService service, @NonNull AppExecutors appExecutors) {
        this.context = applicationContext;
        this.localCityDataSource = localCityDataSource;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    public LiveData<List<City>> getLastSearchedCitiesLiveData() {
        if (lastSearchedCitiesLiveData == null) {
            lastSearchedCitiesLiveData = localCityDataSource.getLastSearchedCities();
        }
        return lastSearchedCitiesLiveData;
    }

    @Nullable
    public Call<ResponseCities> fetchCitiesFromNetwork(@NonNull String cityName, @NonNull DataCallback<List<City>> callback) {
        Call<ResponseCities> call = null;
        if (!Utils.isNetworkConnected(context)) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            call = service.getCity(cityName, LIMIT, EMBED_URL_SCORES, EMBED_URL_IMAGES);
            call.enqueue(new Callback<ResponseCities>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCities> call, @NonNull Response<ResponseCities> response) {
                    final List<City> cities = Utils.createCityListFromResponse(response);
                    if (CollectionUtils.isNotEmpty(cities)) {
                        callback.onComplete(cities, ErrorState.NO_ERROR);

                        addToLastSearchedCities(cities);
                    } else {
                        callback.onComplete(null, ErrorState.EMPTY);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseCities> call, @NonNull Throwable t) {
                    callback.onComplete(null, ErrorState.FAILED);
                }
            });
        }
        return call;
    }

    private void addToLastSearchedCities(List<City> cities) {
        appExecutors.diskIO().execute(() -> {
            int numberOfRows = localCityDataSource.getNumberOfRows();
            for (City city : cities) {
                if (numberOfRows == CITY_STORAGE_LIMIT) {
                    localCityDataSource.deleteFirstCity();
                }
                localCityDataSource.insertCity(city);
            }
        });
    }

    public void removeLastSearchedCities() {
        appExecutors.diskIO().execute(localCityDataSource::deleteAll);
    }

    @NonNull
    public List<City> getComparedCities() {
        return comparedCities;
    }

    public boolean isCompareListContains(@Nullable City city) {
        if (city != null) {
            for (City comparedCity : comparedCities) {
                if (TextUtils.equals(comparedCity.getGeoHash(), city.getGeoHash())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToCompareList(@NonNull City city) {
        if (CollectionUtils.size(comparedCities) == Constants.COMPARE_CAPACITY) {
            return false;
        }
        for (City comparedCity : comparedCities) {
            if (TextUtils.equals(comparedCity.getGeoHash(), city.getGeoHash())) {
                return true;
            }
        }
        comparedCities.add(city);
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_COMPARE));
        return true;
    }

    public void clearCompareList() {
        comparedCities.clear();
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constants.ACTION_COMPARE));
    }
}