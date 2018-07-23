package com.projects.melih.wonderandwander.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Melih Gültekin on 16.07.2018
 */
@IgnoreExtraProperties
@SuppressWarnings({"unused", "WeakerAccess"})
@Entity(tableName = "favorites",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "uId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class FavoritedCity implements Parcelable {
    @PrimaryKey
    @NonNull
    private String geoHash;

    @Nullable
    private String fullName;

    @Nullable
    private String name;

    @Nullable
    private String imageUrl;

    public String userId;

    @Ignore
    public FavoritedCity() {
        geoHash = "";//TODO
    }

    public FavoritedCity(@NonNull String geoHash, @Nullable String fullName, @Nullable String name, @Nullable String imageUrl) {
        this.geoHash = geoHash;
        this.fullName = fullName;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(@NonNull String geoHash) {
        this.geoHash = geoHash;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("geoHash", geoHash);
        result.put("fullName", fullName);
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        return result;
    }

    @Exclude
    @NonNull
    public City toCity() {
        return new City(geoHash, fullName, name, imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.geoHash);
        dest.writeString(this.fullName);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.userId);
    }

    @Ignore
    protected FavoritedCity(Parcel in) {
        this.geoHash = in.readString();
        this.fullName = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<FavoritedCity> CREATOR = new Parcelable.Creator<FavoritedCity>() {
        @Override
        public FavoritedCity createFromParcel(Parcel source) {
            return new FavoritedCity(source);
        }

        @Override
        public FavoritedCity[] newArray(int size) {
            return new FavoritedCity[size];
        }
    };
}