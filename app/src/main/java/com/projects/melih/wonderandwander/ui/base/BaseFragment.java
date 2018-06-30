package com.projects.melih.wonderandwander.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import dagger.android.support.DaggerFragment;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public class BaseFragment extends DaggerFragment {

    protected Context context;
    protected NavigationListener navigationListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.navigationListener = (NavigationListener) context;
    }

    protected void showToast(@StringRes int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@NonNull String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigationListener = null;
    }
}