package com.projects.melih.wonderandwander.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.projects.melih.wonderandwander.ui.cities.CityListFragment;
import com.projects.melih.wonderandwander.ui.profile.LoginFragment;

/**
 * Created by Melih Gültekin on 30.06.2018
 */
class BottomNavigationPagerAdapter extends FragmentStatePagerAdapter {
    static final int SEARCH = 0;
    static final int PROFILE = 1;
    static final int TAB_COUNT = 2;

    BottomNavigationPagerAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case PROFILE:
                fragment = CityListFragment.newInstance();
                break;
            case SEARCH:
            default:
                fragment = LoginFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}