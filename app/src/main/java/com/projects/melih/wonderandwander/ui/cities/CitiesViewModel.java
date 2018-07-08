package com.projects.melih.wonderandwander.ui.cities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.common.SingleLiveEvent;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.CitiesRepository;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public class CitiesViewModel extends ViewModel {

    private final CitiesRepository citiesRepository;
    private final SingleLiveEvent<Integer> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<List<City>> citiesLiveData;
    private final LiveData<List<City>> lastSearchedCitiesLiveData;
    private Call<ResponseCities> callCities;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public CitiesViewModel(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
        errorLiveData = new SingleLiveEvent<>();
        loadingLiveData = new MutableLiveData<>();
        citiesLiveData = new MutableLiveData<>();
        lastSearchedCitiesLiveData = citiesRepository.getLastSearchedCitiesLiveData();
    }

    public SingleLiveEvent<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<List<City>> getCitiesLiveData() {
        return citiesLiveData;
    }

    public LiveData<List<City>> getLastSearchedCitiesLiveData() {
        return lastSearchedCitiesLiveData;
    }

    public void search(@NonNull String cityName) {
        if (callCities != null) {
            callCities.cancel();
        }
        loadingLiveData.setValue(true);
        callCities = citiesRepository.fetchCitiesFromNetwork(cityName, (cities, errorState) -> {
            if (errorState == ErrorState.NO_ERROR) {
                citiesLiveData.setValue(cities);
            } else {
                errorLiveData.setValue(errorState);
            }
            loadingLiveData.setValue(false);
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