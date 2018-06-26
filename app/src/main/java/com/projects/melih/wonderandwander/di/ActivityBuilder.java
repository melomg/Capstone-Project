package com.projects.melih.wonderandwander.di;

import com.projects.melih.wonderandwander.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Module
public abstract class ActivityBuilder {

    @ScopeActivity
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}