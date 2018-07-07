package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
@SuppressWarnings("unused")
public class EmbeddedOfUrbanArea {
    @SerializedName("city:urban_area")
    @Nullable
    private UrbanArea urbanArea;

    @Nullable
    public UrbanArea getUrbanArea() {
        return urbanArea;
    }

    public void setUrbanArea(@Nullable UrbanArea urbanArea) {
        this.urbanArea = urbanArea;
    }
}