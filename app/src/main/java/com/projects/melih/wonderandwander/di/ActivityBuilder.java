package com.projects.melih.wonderandwander.di;

import com.projects.melih.wonderandwander.ui.citydetail.CityDetailActivity;
import com.projects.melih.wonderandwander.ui.main.MainActivity;
import com.projects.melih.wonderandwander.ui.main.MainActivityFragmentBuildersModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Module
public abstract class ActivityBuilder {

    @ScopeActivity
    @ContributesAndroidInjector(modules = {MainActivityFragmentBuildersModule.class})
    abstract MainActivity bindMainActivity();

    @ScopeActivity
    @ContributesAndroidInjector
    abstract CityDetailActivity bindCityDetailActivity();
}