package com.projects.melih.wonderandwander.ui.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.projects.melih.wonderandwander.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationListener {
    @SuppressWarnings("WeakerAccess")
    public static final int NONE = 0;
    @SuppressWarnings("WeakerAccess")
    public static final int LEFT_TO_RIGHT = NONE + 1;
    @SuppressWarnings("WeakerAccess")
    public static final int BOTTOM_TO_TOP = LEFT_TO_RIGHT + 1;

    private BaseFragment fragment;
    private boolean checkOpenFragment;
    private boolean addToBackStack;
    @SlideAnimType
    private int animType;

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        if (checkOpenFragment) {
            openFragment();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.finish();
        }
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment) {
        replaceFragment(newFragment, NONE, true);
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment fragment, @SlideAnimType int animType, boolean addToBackStack) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
        this.animType = animType;
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            openFragment();
        } else {
            checkOpenFragment = true;
        }
    }

    private void openFragment() {
        checkOpenFragment = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
        if (currentFragment != null) {
            // Exit for Previous Fragment
            TransitionSet exitTransitionSet = new TransitionSet();
            // Enter Transition for New Fragment
            TransitionSet enterTransitionSet = new TransitionSet();

            switch (animType) {
                case LEFT_TO_RIGHT:
                    exitTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_left));
                    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right));
                    break;
                case BOTTOM_TO_TOP:
                    exitTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_top));
                    enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_bottom));
                    break;
                case NONE:
                default:
                    //no-op
                    break;
            }
            currentFragment.setExitTransition(exitTransitionSet);
            fragment.setEnterTransition(enterTransitionSet);
        }

        String tag = fragment.getClass().getName();
        if (!fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)) {
            transaction.replace(R.id.container, fragment, tag);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
        }
    }

    protected void showToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {
            NONE,
            LEFT_TO_RIGHT,
            BOTTOM_TO_TOP
    })
    @interface SlideAnimType {
    }
}