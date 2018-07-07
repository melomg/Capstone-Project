package com.projects.melih.wonderandwander.repository.remote;

import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;
import com.projects.melih.wonderandwander.repository.remote.response.ResponsePhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public interface WonderAndWanderService {

    @GET("cities")
    Call<ResponseCities> getCity(@Query(value = "search", encoded = true) String cityName, @Query("limit") int limit, @Query(value = "embed", encoded = true) String scores, @Query(value = "embed", encoded = true) String images);

    @GET("cities/geonameid:{geonameid}")
    Call<City> getCity(@Path("geonameid") int geoNameId);

    @GET("urban_areas/slug:{area}/scores/")
    Call<City> getScores(@Path("area") String area);

    @GET("urban_areas/slug:{area}/images/")
    Call<ResponsePhotos> getImages(@Path("area") String area);
}