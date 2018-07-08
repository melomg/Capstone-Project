package com.projects.melih.wonderandwander.ui.citydetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.databinding.ActivityCityDetailBinding;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class CityDetailActivity extends BaseActivity {
    private static final String KEY_CITY = "key_city";
    private ActivityCityDetailBinding binding;
    private City city;

    public static Intent newIntent(@NonNull Context context, @NonNull City city) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(CityDetailActivity.KEY_CITY, city);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_detail);
        //viewModel = ViewModelProviders.of(this).get(VehicleDetailViewModel.class);
        final Intent intent = getIntent();
        city = intent.getParcelableExtra(KEY_CITY);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(city.getName());
        }
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment) {
        super.replaceFragment(newFragment, R.id.container);
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment, int animType, boolean addToBackStack) {
        super.replaceFragment(newFragment, animType, addToBackStack, R.id.container);
    }
}