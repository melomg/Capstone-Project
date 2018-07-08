package com.projects.melih.wonderandwander.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@SuppressWarnings("unused")
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String uId;

    @Nullable
    private String displayName;

    @Nullable
    private String email;

    @Nullable
    private String phoneNumber;

    @Nullable
    private String photoUrl;

    private boolean isAnonymous;

    public User(@Nullable String displayName, @Nullable String email, @Nullable String phoneNumber,
                @Nullable String photoUrl, boolean isAnonymous, @NonNull String uId) {
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.isAnonymous = isAnonymous;
        this.uId = uId;
    }

    @NonNull
    public String getUId() {
        return uId;
    }

    public void setUId(@NonNull String uId) {
        this.uId = uId;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@Nullable String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}