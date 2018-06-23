package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Melih Gültekin on 22.06.2018
 */
@SuppressWarnings("unused")
public class EmbeddedCity {
    @SerializedName("_embedded")
    @Nullable
    private EmbeddedOfEmbeddedCity embeddedOfEmbeddedCity;

    @SerializedName("matching_alternate_names")
    @Nullable
    private List<Curie> matchingAlternateNames;

    @SerializedName("matching_full_name")
    @Nullable
    private String matchingFullName;

    @Nullable
    public EmbeddedOfEmbeddedCity getEmbeddedOfEmbeddedCity() {
        return embeddedOfEmbeddedCity;
    }

    public void setEmbeddedOfEmbeddedCity(@Nullable EmbeddedOfEmbeddedCity embeddedOfEmbeddedCity) {
        this.embeddedOfEmbeddedCity = embeddedOfEmbeddedCity;
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
}