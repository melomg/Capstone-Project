package com.projects.melih.wonderandwander.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.cities.CitiesViewModel;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 15.06.2018
 */
public class MainActivity extends BaseActivity {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CitiesViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesViewModel.class);
        viewModel.search("Istanbul");
    }
}