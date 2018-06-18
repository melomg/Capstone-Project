package com.projects.melih.wonderandwander.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public interface NavigationListener {
    void onBackPressed();

    void replaceFragment(@NonNull BaseFragment newFragment);

    void replaceFragment(@NonNull BaseFragment newFragment, @BaseActivity.SlideAnimType int animType, boolean addToBackStack);
}