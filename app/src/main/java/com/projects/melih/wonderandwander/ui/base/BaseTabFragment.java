package com.projects.melih.wonderandwander.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class BaseTabFragment extends BaseFragment implements TabNavigationListener {
    private BaseFragment fragment;
    private boolean checkOpenFragment;
    private boolean addToBackStack;
    @IdRes
    private int containerId;

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        if (checkOpenFragment) {
            openChildFragment();
        }
    }

    @Override
    public void openChildFragment(@IdRes int containerId, @NonNull BaseFragment fragment, boolean addToBackStack) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
        this.containerId = containerId;
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            openChildFragment();
        } else {
            checkOpenFragment = true;
        }
    }

    private void openChildFragment() {
        checkOpenFragment = false;
        FragmentManager childFragmentManager = getChildFragmentManager();
        String tag = fragment.getClass().getName();
        if (!childFragmentManager.popBackStackImmediate(tag, 0)) {
            FragmentTransaction transaction = childFragmentManager.beginTransaction();
            Fragment currentFragment = childFragmentManager.findFragmentById(containerId);
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            if (currentFragment != null) {
                // Exit for Previous Fragment
                TransitionSet exitTransitionSet = new TransitionSet();
                // Enter Transition for New Fragment
                TransitionSet enterTransitionSet = new TransitionSet();
                exitTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.slide_left));
                enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right));
                currentFragment.setExitTransition(exitTransitionSet);
                fragment.setEnterTransition(enterTransitionSet);
            }
            transaction.replace(containerId, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
    }
}