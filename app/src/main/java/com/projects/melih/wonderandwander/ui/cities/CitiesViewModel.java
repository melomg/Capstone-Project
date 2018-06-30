package com.projects.melih.wonderandwander.ui.cities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.projects.melih.wonderandwander.common.SingleLiveEvent;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.Photo;
import com.projects.melih.wonderandwander.repository.CitiesRepository;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;
import com.projects.melih.wonderandwander.repository.remote.response.ResponsePhotos;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public class CitiesViewModel extends ViewModel {

    private final CitiesRepository citiesRepository;
    private final SingleLiveEvent<ErrorState> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<List<City>> citiesLiveData;
    private final MutableLiveData<Pair<Integer, List<Photo>>> photosLiveData;
    private Call<ResponseCities> callCities;
    private Call<ResponsePhotos> callImageUrl;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public CitiesViewModel(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
        errorLiveData = new SingleLiveEvent<>();
        loadingLiveData = new MutableLiveData<>();
        citiesLiveData = new MutableLiveData<>();
        photosLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<List<City>> getCitiesLiveData() {
        return citiesLiveData;
    }

    public LiveData<Pair<Integer, List<Photo>>> getPhotosLiveData() {
        return photosLiveData;
    }

    public void search(@NonNull String cityName) {
        if (callCities != null) {
            callCities.cancel();
        }
        loadingLiveData.setValue(true);
        callCities = citiesRepository.fetchCitiesFromNetwork(cityName, (cities, errorState) -> {
            if (errorState == null) {
                citiesLiveData.setValue(cities);
            } else {
                errorLiveData.setValue(errorState);
            }
            loadingLiveData.setValue(false);
        });
    }

    public void getImageUrl(final int position, @NonNull String formattedUrbanAreaName) {
        callImageUrl = citiesRepository.fetchPhotosFromNetwork(formattedUrbanAreaName, (photos, errorState) -> {
            if (errorState == null) {
                photosLiveData.setValue(Pair.create(position, photos));
            } else {
                errorLiveData.setValue(errorState);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (callCities != null) {
            callCities.cancel();
        }
        if (callImageUrl != null) {
            callImageUrl.cancel();
        }
    }
}