package com.projects.melih.wonderandwander.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.CityItem;
import com.projects.melih.wonderandwander.model.CityLinks;
import com.projects.melih.wonderandwander.model.Embedded;
import com.projects.melih.wonderandwander.model.EmbeddedCity;
import com.projects.melih.wonderandwander.model.EmbeddedOfEmbeddedCity;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseCities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public final class Utils {
    private static DecimalFormat numberFormatter;

    private Utils() {
        // no-op
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNetworkConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return ((connectivityManager != null) && (connectivityManager.getActiveNetworkInfo() != null));
    }

    public static void await(@NonNull final View view) {
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true), view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    @NonNull
    public static DecimalFormat getNumberFormatter() {
        if (numberFormatter == null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator('.');
            numberFormatter = new DecimalFormat();
            numberFormatter.setDecimalFormatSymbols(decimalFormatSymbols);
            numberFormatter.setGroupingSize(3);
            numberFormatter.setMaximumFractionDigits(3);
        }
        return numberFormatter;
    }

    @Nullable
    public static String getUrlFormattedUrbanArea(@Nullable String urbanAreaName) {
        if (urbanAreaName == null) {
            return urbanAreaName;
        }
        return urbanAreaName.toLowerCase().replaceAll("\\\\s+", "-");
    }

    @NonNull
    public static List<City> createCityListFromResponse(Response<ResponseCities> response) {
        List<City> cities = new ArrayList<>();
        ResponseCities data = response.body();
        final Embedded embedded = (data == null) ? null : data.getData();
        List<EmbeddedCity> embeddedCities = (embedded == null) ? null : embedded.getSearchResults();
        if (CollectionUtils.isNotEmpty(embeddedCities)) {
            for (EmbeddedCity embeddedCity : embeddedCities) {
                final City city = new City();
                city.setMatchingAlternateNames(embeddedCity.getMatchingAlternateNames());
                city.setMatchingFullName(embeddedCity.getMatchingFullName());
                final EmbeddedOfEmbeddedCity embeddedOfEmbeddedCity = embeddedCity.getEmbeddedOfEmbeddedCity();
                final CityItem cityItem = (embeddedOfEmbeddedCity == null) ? null : embeddedOfEmbeddedCity.getCityItem();
                if (cityItem != null) {
                    city.setFullName(cityItem.getFullName());
                    city.setGeoNameId(cityItem.getGeoNameId());
                    city.setLocation(cityItem.getLocation());
                    city.setName(cityItem.getName());
                    city.setPopulation(cityItem.getPopulation());
                    CityLinks cityLinks = cityItem.getCityLinks();
                    if (cityLinks != null) {
                        city.setAdminDivision(cityLinks.getAdminDivision());
                        city.setAlternateNames(cityLinks.getAlternateNames());
                        city.setCountry(cityLinks.getCountry());
                        city.setTimezone(cityLinks.getTimezone());
                        city.setUrbanArea(cityLinks.getUrbanArea());
                        city.setSelf(cityLinks.getSelf());
                    }
                }
                cities.add(city);
            }
        }
        return cities;
    }
}