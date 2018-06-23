package com.projects.melih.wonderandwander.repository.remote;

import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public interface WonderAndWanderService {

    @GET("cities/?search={city}&embed=city:search-results/city:item")
    Call<ResponseCities> getCities(@Path("city") String cityName);

    @GET("cities/geonameid:{geonameid}")
    Call<City> getCity(@Path("geonameid") int geoNameId);

    @GET("urban_areas/slug:{area}/scores/")
    Call<City> getScores(@Path("area") String area);

    @GET("urban_areas/slug:{area}/images/")
    Call<City> getImages(@Path("area") String area);
}