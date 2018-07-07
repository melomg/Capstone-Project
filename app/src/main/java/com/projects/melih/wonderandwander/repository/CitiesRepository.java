package com.projects.melih.wonderandwander.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.WonderAndWanderService;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

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
    private final Context context;
    private final WonderAndWanderService service;
    private final AppExecutors appExecutors;

    @Inject
    CitiesRepository(@NonNull Context applicationContext, @NonNull WonderAndWanderService service, @NonNull AppExecutors appExecutors) {
        this.context = applicationContext;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    @Nullable
    public Call<ResponseCities> fetchCitiesFromNetwork(@NonNull String cityName, @NonNull DataCallback<List<City>> callback) {
        Call<ResponseCities> call = null;
        if (!Utils.isNetworkConnected(context)) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            call = service.getCity(cityName, LIMIT, "city:search-results/city:item/city:urban_area/ua:scores", "city:search-results/city:item/city:urban_area/ua:images");
            call.enqueue(new Callback<ResponseCities>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCities> call, @NonNull Response<ResponseCities> response) {
                    List<City> cities = Utils.createCityListFromResponse(response);
                    if (CollectionUtils.isNotEmpty(cities)) {
                        callback.onComplete(cities, ErrorState.NO_ERROR);
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
}