package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class CityItem {
    @SerializedName("_embedded")
    @Nullable
    private EmbeddedOfUrbanArea embeddedOfUrbanArea;

    @SerializedName("full_name")
    @Nullable
    private String fullName;

    @SerializedName("geoname_id")
    private int geoNameId;

    @SerializedName("location")
    @Nullable
    private Location location;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("population")
    private double population;

    @SerializedName("_links")
    @Nullable
    private CityLinks cityLinks;

    @Nullable
    public EmbeddedOfUrbanArea getEmbeddedOfUrbanArea() {
        return embeddedOfUrbanArea;
    }

    public void setEmbeddedOfUrbanArea(@Nullable EmbeddedOfUrbanArea embeddedOfUrbanArea) {
        this.embeddedOfUrbanArea = embeddedOfUrbanArea;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
    }

    public int getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(int geoNameId) {
        this.geoNameId = geoNameId;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public CityLinks getCityLinks() {
        return cityLinks;
    }

    public void setCityLinks(CityLinks cityLinks) {
        this.cityLinks = cityLinks;
    }
}