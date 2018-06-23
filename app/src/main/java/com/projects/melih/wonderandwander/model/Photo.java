package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Photo {

    @SerializedName("attribution")
    @Nullable
    private Attribution attribution;

    @SerializedName("image")
    @Nullable
    private Image image;

    @Nullable
    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(@Nullable Attribution attribution) {
        this.attribution = attribution;
    }

    @Nullable
    public Image getImage() {
        return image;
    }

    public void setImage(@Nullable Image image) {
        this.image = image;
    }
}