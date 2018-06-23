package com.projects.melih.wonderandwander.ui.cities;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.projects.melih.wonderandwander.repository.CitiesRepository;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

import javax.inject.Inject;

import retrofit2.Call;
import timber.log.Timber;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public class CitiesViewModel extends ViewModel {

    private final CitiesRepository citiesRepository;
    private Call<ResponseCities> callCities;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public CitiesViewModel(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public void search(@NonNull String cityName) {
        if (callCities != null) {
            callCities.cancel();
        }
        callCities = citiesRepository.fetchCitiesFromNetwork(cityName, new DataCallback<ResponseCities>() {
            @Override
            public void onComplete(@Nullable ResponseCities data, @Nullable ErrorState errorState) {
                if (data != null) {
                    Timber.d(data.toString());
                } else {
                    Timber.e("error");
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (callCities != null) {
            callCities.cancel();
        }
    }
}