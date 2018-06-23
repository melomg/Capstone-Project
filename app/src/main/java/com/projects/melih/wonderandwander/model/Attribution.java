package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Attribution {

    @SerializedName("license")
    @Nullable
    private String license;

    @SerializedName("photographer")
    @Nullable
    private String photographer;

    @SerializedName("site")
    @Nullable
    private String site;

    @SerializedName("source")
    @Nullable
    private String source;

    @Nullable
    public String getLicense() {
        return license;
    }

    public void setLicense(@Nullable String license) {
        this.license = license;
    }

    @Nullable
    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(@Nullable String photographer) {
        this.photographer = photographer;
    }

    @Nullable
    public String getSite() {
        return site;
    }

    public void setSite(@Nullable String site) {
        this.site = site;
    }

    @Nullable
    public String getSource() {
        return source;
    }

    public void setSource(@Nullable String source) {
        this.source = source;
    }
}