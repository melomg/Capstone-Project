package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
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
    private LatLng latLng;

    @Nullable
    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(@Nullable String geoHash) {
        this.geoHash = geoHash;
    }

    @Nullable
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(@Nullable LatLng latLng) {
        this.latLng = latLng;
    }
}