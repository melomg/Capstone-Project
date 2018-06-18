package com.projects.melih.wonderandwander.di;

import com.projects.melih.wonderandwander.WonderAndWanderApplication;
import com.projects.melih.wonderandwander.repository.ApiAndDataModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        SingletonModule.class,
        ActivityBuilder.class,
        ApiAndDataModule.class
})
public interface SingletonComponent extends AndroidInjector<WonderAndWanderApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<WonderAndWanderApplication> {
    }
}