package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Score {

    @SerializedName("categories")
    @Nullable
    private List<Category> categories;

    @SerializedName("summary")
    @Nullable
    private String summary;

    @SerializedName("teleport_city_score")
    private float teleportCityScore;

    @Nullable
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(@Nullable List<Category> categories) {
        this.categories = categories;
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
}