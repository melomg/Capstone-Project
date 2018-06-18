package com.projects.melih.wonderandwander.repository.remote;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public class ErrorState {
    public static final int STATE_FAILED = 0;
    public static final int STATE_NO_NETWORK = STATE_FAILED + 1;
    public static final int STATE_EMPTY = STATE_NO_NETWORK + 1;

    public static final ErrorState FAILED = new ErrorState(STATE_FAILED);
    public static final ErrorState NO_NETWORK = new ErrorState(STATE_NO_NETWORK);
    public static final ErrorState EMPTY = new ErrorState(STATE_EMPTY);

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    @State
    private final int state;
    private final String errorMessage;

    public ErrorState(@State int state) {
        this(state, null);
    }

    @SuppressWarnings("WeakerAccess")
    public ErrorState(@State int state, @Nullable String errorMessage) {
        this.state = state;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getState() {
        return state;
    }

    public static ErrorState error(@Nullable String errorMessage) {
        return new ErrorState(STATE_FAILED, errorMessage);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {
            STATE_FAILED,
            STATE_NO_NETWORK,
            STATE_EMPTY
    })
    public @interface State {
    }
}