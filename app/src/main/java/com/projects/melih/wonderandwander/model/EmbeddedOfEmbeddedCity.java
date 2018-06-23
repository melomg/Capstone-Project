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
    private City city;

    @Nullable
    public City getCity() {
        return city;
    }

    public void setCity(@Nullable City city) {
        this.city = city;
    }
}