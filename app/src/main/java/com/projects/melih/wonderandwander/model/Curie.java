package com.projects.melih.wonderandwander.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class Curie implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeString(this.name);
        dest.writeByte(this.templated ? (byte) 1 : (byte) 0);
    }

    public Curie() {
    }

    protected Curie(Parcel in) {
        this.href = in.readString();
        this.name = in.readString();
        this.templated = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Curie> CREATOR = new Parcelable.Creator<Curie>() {
        @Override
        public Curie createFromParcel(Parcel source) {
            return new Curie(source);
        }

        @Override
        public Curie[] newArray(int size) {
            return new Curie[size];
        }
    };
}