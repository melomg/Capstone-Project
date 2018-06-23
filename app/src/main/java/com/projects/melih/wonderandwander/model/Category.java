package com.projects.melih.wonderandwander.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Category {

    @SerializedName("color")
    @Nullable
    private String color;

    @SerializedName("name")
    @Nullable
    private String name;

    @SerializedName("score_out_of_10")
    private double scoreOutOf10;

    @Nullable
    public String getColor() {
        return color;
    }

    public void setColor(@Nullable String color) {
        this.color = color;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public double getScoreOutOf10() {
        return scoreOutOf10;
    }

    public void setScoreOutOf10(double scoreOutOf10) {
        this.scoreOutOf10 = scoreOutOf10;
    }
}