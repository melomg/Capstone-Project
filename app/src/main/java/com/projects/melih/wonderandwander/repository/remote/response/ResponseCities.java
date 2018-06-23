package com.projects.melih.wonderandwander.repository.remote.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.projects.melih.wonderandwander.model.Embedded;

/**
 * Created by Melih Gültekin on 22.06.2018
 */
@SuppressWarnings("unused")
public class ResponseCities extends BaseResponse {
    @SerializedName("_embedded")
    @Nullable
    private Embedded data;

    @Nullable
    public Embedded getData() {
        return data;
    }

    public void setData(@Nullable Embedded data) {
        this.data = data;
    }
}