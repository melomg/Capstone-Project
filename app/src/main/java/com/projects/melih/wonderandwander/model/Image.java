package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Image {

    @SerializedName("mobile")
    @Nullable
    private String mobile;

    @SerializedName("web")
    @Nullable
    private String web;

    @Nullable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(@Nullable String mobile) {
        this.mobile = mobile;
    }

    @Nullable
    public String getWeb() {
        return web;
    }

    public void setWeb(@Nullable String web) {
        this.web = web;
    }
}