package com.projects.melih.wonderandwander.repository.remote;

import android.support.annotation.Nullable;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
public interface DataCallback<T> {
    void onComplete(@Nullable T data, @ErrorState.Code int errorState);
}