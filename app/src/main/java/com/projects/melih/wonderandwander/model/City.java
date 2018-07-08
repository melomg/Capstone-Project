package com.projects.melih.wonderandwander.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Entity(tableName = "last_searched_cities")
public class City implements Parcelable {
    @PrimaryKey
    @NonNull
    private String geoHash;

    @Nullable
    private String fullName;

    private int geoNameId;

    @Nullable
    private String name;

    private double population;

    @Ignore
    @Nullable
    private Curie adminDivision;

    @Ignore
    @Nullable
    private Curie alternateNames;

    @Ignore
    @Nullable
    private Curie country;

    @Ignore
    @Nullable
    private Curie timezone;

    @Ignore
    @Nullable
    private Curie urbanArea;

    @Ignore
    @Nullable
    private Curie self;

    @Ignore
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

    private double latitude;

    private double longitude;

    public City() {
        geoHash = "";//TODO create a unique string
    }

    @NonNull
    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(@NonNull String geoHash) {
        this.geoHash = geoHash;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldCity, @NonNull City newCity) {
            return TextUtils.equals(oldCity.getGeoHash(), newCity.getGeoHash());
        }

        @Override
        public boolean areContentsTheSame(@NonNull City oldCity, @NonNull City newCity) {
            return oldCity.equals(newCity);
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.geoHash);
        dest.writeString(this.fullName);
        dest.writeInt(this.geoNameId);
        dest.writeString(this.name);
        dest.writeDouble(this.population);
        dest.writeParcelable(this.adminDivision, flags);
        dest.writeParcelable(this.alternateNames, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.timezone, flags);
        dest.writeParcelable(this.urbanArea, flags);
        dest.writeParcelable(this.self, flags);
        dest.writeList(this.matchingAlternateNames);
        dest.writeString(this.matchingFullName);
        dest.writeString(this.imageUrl);
        dest.writeList(this.scoresOfCategories);
        dest.writeString(this.summary);
        dest.writeFloat(this.teleportCityScore);
        dest.writeString(this.continent);
        dest.writeByte(this.isGovernmentPartner ? (byte) 1 : (byte) 0);
        dest.writeString(this.mayor);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected City(Parcel in) {
        this.geoHash = in.readString();
        this.fullName = in.readString();
        this.geoNameId = in.readInt();
        this.name = in.readString();
        this.population = in.readDouble();
        this.adminDivision = in.readParcelable(Curie.class.getClassLoader());
        this.alternateNames = in.readParcelable(Curie.class.getClassLoader());
        this.country = in.readParcelable(Curie.class.getClassLoader());
        this.timezone = in.readParcelable(Curie.class.getClassLoader());
        this.urbanArea = in.readParcelable(Curie.class.getClassLoader());
        this.self = in.readParcelable(Curie.class.getClassLoader());
        this.matchingAlternateNames = new ArrayList<Curie>();
        in.readList(this.matchingAlternateNames, Curie.class.getClassLoader());
        this.matchingFullName = in.readString();
        this.imageUrl = in.readString();
        this.scoresOfCategories = new ArrayList<Category>();
        in.readList(this.scoresOfCategories, Category.class.getClassLoader());
        this.summary = in.readString();
        this.teleportCityScore = in.readFloat();
        this.continent = in.readString();
        this.isGovernmentPartner = in.readByte() != 0;
        this.mayor = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}