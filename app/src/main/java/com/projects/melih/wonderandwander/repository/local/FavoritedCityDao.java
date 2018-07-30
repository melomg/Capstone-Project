package com.projects.melih.wonderandwander.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.projects.melih.wonderandwander.model.FavoritedCity;

import java.util.List;

/**
 * Created by Melih Gültekin on 16.07.2018
 */
@Dao
public interface FavoritedCityDao {
    @Query("SELECT * FROM favorites")
    List<FavoritedCity> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FavoritedCity> favoritedCities);

    @Query("DELETE FROM favorites")
    int deleteAll();
}