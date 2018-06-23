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
    private LatLng latLng;

    @Nullable
    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(@Nullable String geoHash) {
        this.geoHash = geoHash;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}