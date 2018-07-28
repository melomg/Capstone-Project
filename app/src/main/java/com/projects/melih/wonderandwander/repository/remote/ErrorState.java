package com.projects.melih.wonderandwander.repository.remote;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public final class ErrorState {
    public static final int NO_ERROR = 0;
    public static final int FAILED = NO_ERROR + 1;
    public static final int NO_NETWORK = FAILED + 1;
    public static final int EMPTY = NO_NETWORK + 1;
    public static final int FAILED_GET_USER = EMPTY + 1;
    public static final int FAILED_SAVE_USER = FAILED_GET_USER + 1;
    public static final int FAILED_DELETE_USER = FAILED_SAVE_USER + 1;
    public static final int ALREADY_ADDED_TO_COMPARE_LIST = FAILED_DELETE_USER + 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {
            NO_ERROR,
            FAILED,
            NO_NETWORK,
            EMPTY
    })
    public @interface Code {
    }

    private ErrorState() {
        //no-op
    }
}