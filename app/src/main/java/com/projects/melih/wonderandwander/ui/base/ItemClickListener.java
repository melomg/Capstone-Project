package com.projects.melih.wonderandwander.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Melih Gültekin on 05.05.2018
 */
public interface ItemClickListener<T> {
    void onItemClick(@NonNull T object);
}