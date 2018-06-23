package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 22.06.2018
 */
@SuppressWarnings("unused")
public class CityLinks {
    @SerializedName("city:admin1_division")
    @Nullable
    private Curie adminDivision;

    @SerializedName("city:alternate-names")
    @Nullable
    private Curie alternateNames;

    @SerializedName("city:country")
    @Nullable
    private Curie country;

    @SerializedName("city:timezone")
    @Nullable
    private Curie timezone;

    @SerializedName("city:urban_area")
    @Nullable
    private Curie urbanArea;

    @SerializedName("self")
    @Nullable
    private Curie self;

    @Nullable
    public Curie getAdminDivision() {
        return adminDivision;
    }

    public void setAdminDivision(@Nullable Curie adminDivision) {
        this.adminDivision = adminDivision;
    }

    @Nullable
    public Curie getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(@Nullable Curie alternateNames) {
        this.alternateNames = alternateNames;
    }

    @Nullable
    public Curie getCountry() {
        return country;
    }

    public void setCountry(@Nullable Curie country) {
        this.country = country;
    }

    @Nullable
    public Curie getTimezone() {
        return timezone;
    }

    public void setTimezone(@Nullable Curie timezone) {
        this.timezone = timezone;
    }

    @Nullable
    public Curie getUrbanArea() {
        return urbanArea;
    }

    public void setUrbanArea(@Nullable Curie urbanArea) {
        this.urbanArea = urbanArea;
    }

    @Nullable
    public Curie getSelf() {
        return self;
    }

    public void setSelf(@Nullable Curie self) {
        this.self = self;
    }
}