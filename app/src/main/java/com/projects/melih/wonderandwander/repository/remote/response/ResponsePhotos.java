package com.projects.melih.wonderandwander.repository.remote.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.projects.melih.wonderandwander.model.Embedded;
import com.projects.melih.wonderandwander.model.Photo;

import java.util.List;

/**
 * Created by Melih Gültekin on 28.06.2018
 */
@SuppressWarnings("unused")
public class ResponsePhotos extends BaseResponse {
    @SerializedName("photos")
    @Nullable
    private List<Photo> photos;

    @Nullable
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(@Nullable List<Photo> photos) {
        this.photos = photos;
    }
}