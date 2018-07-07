package com.projects.melih.wonderandwander.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public interface TabNavigationListener {
    void openChildFragment(int containerId, @NonNull BaseFragment fragment, boolean addToBackStack);
}