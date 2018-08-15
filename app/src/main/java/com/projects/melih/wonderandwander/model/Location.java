package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Location {

    @SerializedName("geohash")
    @Nullable
    private String geoHash;

    @SerializedName("latlon")
    @Nullable
    private LatLon latLng;

    @Nullable
    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(@Nullable String geoHash) {
        this.geoHash = geoHash;
    }

    @Nullable
    public LatLon getLatLng() {
        return latLng;
    }

    public void setLatLng(@Nullable LatLon latLng) {
        this.latLng = latLng;
    }
}