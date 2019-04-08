package com.projects.melih.wonderandwander.common;

import com.projects.melih.wonderandwander.BuildConfig;

import java.util.TimeZone;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public final class Constants {
    public static final String UNKNOWN_ERROR = "Unknown error";
    public static final String ACTION_COMPARE = BuildConfig.APPLICATION_ID + ".ACTION_COMPARE";
    public static final int COMPARE_CAPACITY = 2;
    public static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");

    private Constants() {
        //no-op
    }
}