package com.projects.melih.wonderandwander.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Category implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeString(this.name);
        dest.writeDouble(this.scoreOutOf10);
    }

    public Category() {
    }

    private Category(Parcel in) {
        this.color = in.readString();
        this.name = in.readString();
        this.scoreOutOf10 = in.readDouble();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}