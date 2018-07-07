package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.projects.melih.wonderandwander.repository.remote.response.ResponsePhotos;
import com.projects.melih.wonderandwander.repository.remote.response.ResponseScores;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
@SuppressWarnings("unused")
public class EmbeddedOfImagesAndScores {
    @SerializedName("ua:images")
    @Nullable
    private ResponsePhotos responsePhotos;

    @SerializedName("ua:scores")
    @Nullable
    private ResponseScores responseScores;

    @Nullable
    public ResponsePhotos getResponsePhotos() {
        return responsePhotos;
    }

    public void setResponsePhotos(@Nullable ResponsePhotos responsePhotos) {
        this.responsePhotos = responsePhotos;
    }

    @Nullable
    public ResponseScores getResponseScores() {
        return responseScores;
    }

    public void setResponseScores(@Nullable ResponseScores responseScores) {
        this.responseScores = responseScores;
    }
}