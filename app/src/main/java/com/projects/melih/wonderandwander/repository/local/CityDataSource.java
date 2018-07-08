package com.projects.melih.wonderandwander.repository.local;

import android.arch.lifecycle.LiveData;

import com.projects.melih.wonderandwander.model.City;

import java.util.List;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public interface CityDataSource {
    /**
     * Gets last searched cities from the data source.
     *
     * @return last searched cities from the data source.
     */
    LiveData<List<City>> getLastSearchedCities();

    /**
     * Inserts the city in the data source, or, if it is an existing city, it updates it.
     *
     * @param city the city to be inserted or updated.
     * @return the new rowId
     */
    long insertCity(City city);

    /**
     * Deletes first added city from the data source.
     *
     * @return the number of rows removed
     */
    int deleteFirstCity();

    /**
     * Deletes all last searched cities from the data source.
     *
     * @return the number of rows removed
     */
    int deleteAll();

    /**
     * @return the number of rows exists in data source
     */
    int getNumberOfRows();
}