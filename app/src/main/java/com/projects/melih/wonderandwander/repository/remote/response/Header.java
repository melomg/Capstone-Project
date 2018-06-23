package com.projects.melih.wonderandwander.repository.remote.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.projects.melih.wonderandwander.model.Curie;

import java.util.List;

/**
 * Created by Melih Gültekin on 21.06.2018
 */
public class Header {
    @SerializedName("curies")
    @Nullable
    private List<Curie> curies;

    @SerializedName("self")
    @Nullable
    private Curie self;

    @Nullable
    public List<Curie> getCuries() {
        return curies;
    }

    public void setCuries(@Nullable List<Curie> curies) {
        this.curies = curies;
    }

    @Nullable
    public Curie getSelf() {
        return self;
    }

    public void setSelf(@Nullable Curie self) {
        this.self = self;
    }
}