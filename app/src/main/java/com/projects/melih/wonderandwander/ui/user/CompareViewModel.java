package com.projects.melih.wonderandwander.ui.user;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.projects.melih.wonderandwander.common.Constants;
import com.projects.melih.wonderandwander.common.SingleLiveEvent;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.CitiesRepository;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 24.07.2018
 */
public class CompareViewModel extends ViewModel {
    private final CitiesRepository citiesRepository;
    @SuppressLint("StaticFieldLeak")
    private final Context applicationContext;
    private final MutableLiveData<List<City>> comparedCitiesLiveData;
    private final SingleLiveEvent<Integer> errorLiveData;
    private final BroadcastReceiver receiverCompare = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateCities();
        }
    };

    @SuppressWarnings("WeakerAccess")
    @Inject
    public CompareViewModel(CitiesRepository citiesRepository, Context applicationContext) {
        this.applicationContext = applicationContext;
        this.citiesRepository = citiesRepository;
        comparedCitiesLiveData = new MutableLiveData<>();
        errorLiveData = new SingleLiveEvent<>();
        updateCities();
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(receiverCompare, new IntentFilter(Constants.ACTION_COMPARE));
    }

    public MutableLiveData<List<City>> getComparedCitiesLiveData() {
        return comparedCitiesLiveData;
    }

    public SingleLiveEvent<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public void addToCompareList(@Nullable City city) {
        if (city != null) {
            if (citiesRepository.isCompareListContains(city)) {
                errorLiveData.setValue(ErrorState.ALREADY_ADDED_TO_COMPARE_LIST);
            } else {
                citiesRepository.addToCompareList(city);
            }
        }
    }

    private void updateCities() {
        comparedCitiesLiveData.setValue(citiesRepository.getComparedCities());
    }

    @Override
    protected void onCleared() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(receiverCompare);
        super.onCleared();
    }
}