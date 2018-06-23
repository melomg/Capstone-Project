package com.projects.melih.wonderandwander.repository.remote.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Melih Gültekin on 20.06.2018
 */
@SuppressWarnings("unused")
public class BaseResponse {
    @SerializedName("_links")
    @Nullable
    private Header header;

    @SerializedName("count")
    private int count;

    @Nullable
    public Header getHeader() {
        return header;
    }

    public int getCount() {
        return count;
    }
}