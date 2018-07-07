package com.projects.melih.wonderandwander.ui.main;

import com.projects.melih.wonderandwander.di.ScopeFragment;
import com.projects.melih.wonderandwander.di.ScopeUser;
import com.projects.melih.wonderandwander.ui.cities.CityListFragment;
import com.projects.melih.wonderandwander.ui.user.CompareFragment;
import com.projects.melih.wonderandwander.ui.user.LoginFragment;
import com.projects.melih.wonderandwander.ui.user.ProfileFragment;
import com.projects.melih.wonderandwander.ui.user.UserFragment;
import com.projects.melih.wonderandwander.ui.user.UserTabFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Melih Gültekin on 28.06.2018
 */
@Module
public abstract class MainActivityFragmentBuildersModule {
    @ScopeFragment
    @ContributesAndroidInjector
    abstract CityListFragment bindCityListFragment();

    @ScopeFragment
    @ContributesAndroidInjector
    abstract UserTabFragment bindUserTabFragment();

    @ScopeUser
    @ContributesAndroidInjector
    abstract UserFragment bindUserFragment();

    @ScopeFragment
    @ContributesAndroidInjector
    abstract LoginFragment bindLoginFragment();

    @ScopeUser
    @ContributesAndroidInjector
    abstract ProfileFragment bindProfileFragment();

    @ScopeUser
    @ContributesAndroidInjector
    abstract CompareFragment bindCompareFragment();
}