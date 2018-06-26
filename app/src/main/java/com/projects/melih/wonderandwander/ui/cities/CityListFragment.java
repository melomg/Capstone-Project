package com.projects.melih.wonderandwander.ui.cities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentCityListBinding;
import com.projects.melih.wonderandwander.ui.main.MainActivity;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import javax.inject.Inject;

import timber.log.Timber;

public class CityListFragment extends BaseFragment implements View.OnClickListener {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private FragmentCityListBinding binding;
    private CitiesViewModel citiesViewModel;

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false);
        //noinspection ConstantConditions
        citiesViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(CitiesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.await(v);
        switch (v.getId()) {
            case R.id.search:
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();
                    final FragmentActivity activity = getActivity();
                    if (activity != null) {
                        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .setFilter(typeFilter)
                                .build(activity);
                        activity.startActivityForResult(intent, MainActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    }
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Timber.e(e);
                }
                break;
        }
    }
}