package com.projects.melih.wonderandwander.repository.local;

import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.model.User;

import java.util.List;

/**
 * Created by Melih Gültekin on 16.07.2018
 */
public interface FavoritesDataSource {

    /**
     * Inserts all favorited cities tothe data source, or, if it is an existing, it updates it.
     *
     * @param favoritedCities list to be inserted or updated.
     */
    void insertAll(List<FavoritedCity> favoritedCities);

    /**
     * Deletes all favorited cities from the data source.
     *
     * @return the number of rows removed
     */
    int deleteAll();

    /**
     * @param userId to query
     * @return a list of favorited cities of user.
     */
    List<FavoritedCity> getFavoritesOfUser(@NonNull String userId);
}