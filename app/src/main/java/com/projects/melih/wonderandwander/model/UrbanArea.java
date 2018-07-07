package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
@SuppressWarnings("unused")
public class UrbanArea {
    @SerializedName("_embedded")
    @Nullable
    private EmbeddedOfImagesAndScores embeddedOfImagesAndScores;

    @SerializedName("continent")
    @Nullable
    private String continent;

    @SerializedName("full_name")
    @Nullable
    private String fullName;

    @SerializedName("is_government_partner")
    private boolean isGovernmentPartner;

    @SerializedName("mayor")
    @Nullable
    private String mayor;

    //TODO @SerializedName("bounding_box")

    @Nullable
    public EmbeddedOfImagesAndScores getEmbeddedOfImagesAndScores() {
        return embeddedOfImagesAndScores;
    }

    public void setEmbeddedOfImagesAndScores(@Nullable EmbeddedOfImagesAndScores embeddedOfImagesAndScores) {
        this.embeddedOfImagesAndScores = embeddedOfImagesAndScores;
    }

    @Nullable
    public String getContinent() {
        return continent;
    }

    public void setContinent(@Nullable String continent) {
        this.continent = continent;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
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
}