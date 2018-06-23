package com.projects.melih.wonderandwander.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.WonderAndWanderService;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;


import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.projects.melih.wonderandwander.common.Constants.UNKNOWN_ERROR;

/**
 * Created by Melih Gültekin on 21.06.2018
 */
@Singleton
public class CitiesRepository {
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
    public Call<ResponseCities> fetchCitiesFromNetwork(@NonNull String cityName, @NonNull DataCallback<ResponseCities> callback) {
        Call<ResponseCities> call = null;
        if (!Utils.isNetworkConnected(context)) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            call = service.getCities(cityName);
            call.enqueue(new Callback<ResponseCities>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCities> call, @NonNull Response<ResponseCities> response) {
                    final ResponseCities cities = response.body();
                    //TODO
                }

                @Override
                public void onFailure(@NonNull Call<ResponseCities> call, @NonNull Throwable t) {
                    final String message = t.getMessage();
                    callback.onComplete(null, ErrorState.error((message == null) ? UNKNOWN_ERROR : message));
                }
            });
        }
        return call;
    }
}