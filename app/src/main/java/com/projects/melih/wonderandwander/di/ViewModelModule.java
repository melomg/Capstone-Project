package com.projects.melih.wonderandwander.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.projects.melih.wonderandwander.ui.cities.CitiesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel.class)
    abstract ViewModel bindRecipesViewModel(CitiesViewModel citiesViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(WonderAndWanderViewModelFactory viewModelFactory);
}