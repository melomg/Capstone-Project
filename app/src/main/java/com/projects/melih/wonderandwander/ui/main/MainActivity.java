package com.projects.melih.wonderandwander.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.cities.CitiesViewModel;
import com.projects.melih.wonderandwander.ui.cities.CityListFragment;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 15.06.2018
 */
public class MainActivity extends BaseActivity {
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private CitiesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, CityListFragment.newInstance())
                    .addToBackStack("")
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                final CharSequence name = place.getName();
                if (name != null) {
                    viewModel.search(name.toString());
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Timber.i(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}