package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Curie {

    @SerializedName("href")
    @Nullable
    private String href;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("templated")
    private boolean templated;

    @Nullable
    public String getHref() {
        return href;
    }

    public void setHref(@Nullable String href) {
        this.href = href;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public boolean isTemplated() {
        return templated;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }
}