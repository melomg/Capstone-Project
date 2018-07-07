package com.projects.melih.wonderandwander.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class City {
    @Nullable
    private String fullName;

    private int geoNameId;

    @Nullable
    private Location location;

    @Nullable
    private String name;

    private double population;

    @Nullable
    private Curie adminDivision;

    @Nullable
    private Curie alternateNames;

    @Nullable
    private Curie country;

    @Nullable
    private Curie timezone;

    @Nullable
    private Curie urbanArea;

    @Nullable
    private Curie self;

    @Nullable
    private List<Curie> matchingAlternateNames;

    @Nullable
    private String matchingFullName;

    @Nullable
    private String imageUrl;

    @Nullable
    private List<Category> scoresOfCategories;

    @Nullable
    private String summary;

    private float teleportCityScore;

    @Nullable
    private String continent;

    private boolean isGovernmentPartner;

    @Nullable
    private String mayor;

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

    @Nullable
    public List<Curie> getMatchingAlternateNames() {
        return matchingAlternateNames;
    }

    public void setMatchingAlternateNames(@Nullable List<Curie> matchingAlternateNames) {
        this.matchingAlternateNames = matchingAlternateNames;
    }

    @Nullable
    public String getMatchingFullName() {
        return matchingFullName;
    }

    public void setMatchingFullName(@Nullable String matchingFullName) {
        this.matchingFullName = matchingFullName;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public List<Category> getScoresOfCategories() {
        return scoresOfCategories;
    }

    public void setScoresOfCategories(@Nullable List<Category> scoresOfCategories) {
        this.scoresOfCategories = scoresOfCategories;
    }

    @Nullable
    public String getSummary() {
        return summary;
    }

    public void setSummary(@Nullable String summary) {
        this.summary = summary;
    }

    public float getTeleportCityScore() {
        return teleportCityScore;
    }

    public void setTeleportCityScore(float teleportCityScore) {
        this.teleportCityScore = teleportCityScore;
    }

    @Nullable
    public String getContinent() {
        return continent;
    }

    public void setContinent(@Nullable String continent) {
        this.continent = continent;
    }

    public boolean isGovernmentPartner() {
        return isGovernmentPartner;
    }

    public void setGovernmentPartner(boolean governmentPartner) {
        isGovernmentPartner = governmentPartner;
    }

    @Nullable
    public String getMayor() {
        return mayor;
    }

    public void setMayor(@Nullable String mayor) {
        this.mayor = mayor;
    }

    public static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldCity, @NonNull City newCity) {
            final Location oldCityLocation = oldCity.getLocation();
            final Location newCityLocation = newCity.getLocation();
            return TextUtils.equals(oldCity.getMatchingFullName(), newCity.getMatchingFullName())
                    && ((oldCityLocation != null) && (newCityLocation != null)
                    && TextUtils.equals(oldCityLocation.getGeoHash(), newCityLocation.getGeoHash()));
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldCity, @NonNull City newCity) {
            return oldCity.equals(newCity);
        }
    };
}