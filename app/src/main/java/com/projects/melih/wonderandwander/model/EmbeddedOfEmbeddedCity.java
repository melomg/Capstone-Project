package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 22.06.2018
 */
@SuppressWarnings("unused")
public class EmbeddedOfEmbeddedCity {
    @SerializedName("city:item")
    @Nullable
    private CityItem cityItem;

    @Nullable
    public CityItem getCityItem() {
        return cityItem;
    }

    public void setCityItem(@Nullable CityItem cityItem) {
        this.cityItem = cityItem;
    }
}