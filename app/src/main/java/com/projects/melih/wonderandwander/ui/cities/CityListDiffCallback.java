package com.projects.melih.wonderandwander.ui.cities;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.projects.melih.wonderandwander.model.City;

/**
 * Created by Melih Gültekin on 14.07.2018
 */
public class CityListDiffCallback extends DiffUtil.ItemCallback<City> {
    @Override
    public boolean areItemsTheSame(@NonNull City oldCity, @NonNull City newCity) {
        return TextUtils.equals(oldCity.getGeoHash(), newCity.getGeoHash());
    }

    @Override
    public boolean areContentsTheSame(@NonNull City oldCity, @NonNull City newCity) {
        return oldCity.equals(newCity);
    }
}