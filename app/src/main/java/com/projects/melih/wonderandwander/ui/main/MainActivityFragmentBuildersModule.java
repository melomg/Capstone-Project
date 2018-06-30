package com.projects.melih.wonderandwander.ui.main;

import com.projects.melih.wonderandwander.di.ScopeFragment;
import com.projects.melih.wonderandwander.ui.cities.CityListFragment;

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
}