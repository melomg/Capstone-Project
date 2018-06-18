package com.projects.melih.wonderandwander.di;

import android.content.Context;

import com.projects.melih.wonderandwander.WonderAndWanderApplication;
import com.projects.melih.wonderandwander.common.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Module(includes = {ViewModelModule.class})
public class SingletonModule {

    @Provides
    @Singleton
    Context provideContext(WonderAndWanderApplication application) {
        return application;
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}