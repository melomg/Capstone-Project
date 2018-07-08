package com.projects.melih.wonderandwander.repository.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.model.City;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
@Singleton
public class LocalCityDataSource implements CityDataSource {
    private final CityDao cityDao;

    @Inject
    public LocalCityDataSource(@NonNull CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public LiveData<List<City>> getLastSearchedCities() {
        return cityDao.getLastSearchedCities();
    }

    @Override
    public long insertCity(City city) {
        return cityDao.insert(city);
    }

    @Override
    public int deleteFirstCity() {
        return cityDao.deleteFirst();
    }

    @Override
    public int deleteAll() {
        return cityDao.deleteAll();
    }

    @Override
    public int getNumberOfRows() {
        return cityDao.getNumberOfRows();
    }
}