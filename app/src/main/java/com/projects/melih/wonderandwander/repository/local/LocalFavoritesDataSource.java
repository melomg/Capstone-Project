package com.projects.melih.wonderandwander.repository.local;

import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.model.FavoritedCity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Melih Gültekin on 16.07.2018
 */
@Singleton
public class LocalFavoritesDataSource implements FavoritesDataSource {
    private final FavoritedCityDao favoritedCityDao;

    @Inject
    public LocalFavoritesDataSource(@NonNull FavoritedCityDao favoritedCityDao) {
        this.favoritedCityDao = favoritedCityDao;
    }

    @Override
    public List<FavoritedCity> getFavorites() {
        return favoritedCityDao.getFavorites();
    }

    @Override
    public void insertAll(List<FavoritedCity> favoritedCities) {
        favoritedCityDao.insertAll(favoritedCities);
    }

    @Override
    public int deleteAll() {
        return favoritedCityDao.deleteAll();
    }
}