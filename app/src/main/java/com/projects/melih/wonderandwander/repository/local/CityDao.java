package com.projects.melih.wonderandwander.repository.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.projects.melih.wonderandwander.model.City;

import java.util.List;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
@Dao
public interface CityDao {
    @Query("SELECT * from last_searched_cities ORDER BY timeSpan DESC")
    LiveData<List<City>> getLastSearchedCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(City city);
    //DELETE from user where user_id IN (Select user_id from user limit 1)
    @Query("DELETE FROM last_searched_cities WHERE geoHash IN (SELECT geoHash FROM last_searched_cities LIMIT 1)")
    int deleteFirst();

    @Query("DELETE FROM last_searched_cities")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM last_searched_cities")
    int getNumberOfRows();
}