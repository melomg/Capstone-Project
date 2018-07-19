package com.projects.melih.wonderandwander.ui.cities;

import android.app.Activity;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentCityListBinding;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.citydetail.CityDetailActivity;
import com.projects.melih.wonderandwander.ui.user.UserViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 26.06.2018
 */
public class CityListFragment extends BaseFragment implements View.OnClickListener {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private FragmentCityListBinding binding;
    private CitiesViewModel citiesViewModel;
    private UserViewModel userViewModel;
    private CityListAdapter searchAdapter;
    private CityListAdapter lastSearchedCitiesAdapter;

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false);
        //noinspection ConstantConditions
        citiesViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(CitiesViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        //TODO show loading
        citiesViewModel.getCitiesLiveData().observe(this, cities -> {
            searchAdapter.submitCityList(cities, userViewModel.getFavoritesLiveData().getValue());
            binding.lastSearchesDivider.setVisibility(CollectionUtils.isNotEmpty(cities) ? View.VISIBLE : View.INVISIBLE);
        });
        citiesViewModel.getLastSearchedCitiesLiveData().observe(this, cities -> {
            lastSearchedCitiesAdapter.submitCityList(cities, userViewModel.getFavoritesLiveData().getValue());
            if (CollectionUtils.isNotEmpty(cities)) {
                binding.clearHistory.setVisibility(View.VISIBLE);
                binding.emptyViewLastSearches.setVisibility(View.GONE);
            } else {
                binding.clearHistory.setVisibility(View.INVISIBLE);
                binding.emptyViewLastSearches.setVisibility(View.VISIBLE);
            }
        });
        userViewModel.getFavoritesLiveData().observe(this, favoritedCities -> {
            if (favoritedCities != null) {
                lastSearchedCitiesAdapter.updateCitiesFavoriteInfo(favoritedCities);
                searchAdapter.updateCitiesFavoriteInfo(favoritedCities);
            }
        });
        userViewModel.getErrorLiveData().observe(this, errorCode -> {
            if (errorCode != null) {
                switch (errorCode) {
                    case ErrorState.NO_NETWORK:
                        showToast(R.string.network_error);
                        break;
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchAdapter = new CityListAdapter(new CityListAdapter.CityItemListener() {
            @Override
            public void onFavoriteDelete(@NonNull City city) {
                userViewModel.removeFromFavoriteList(city);
            }

            @Override
            public void onFavoriteAdded(@NonNull City city) {
                userViewModel.addCityToFavoriteList(city);
            }

            @Override
            public void onItemClick(@NonNull City city) {
                startActivity(CityDetailActivity.newIntent(context, city));
            }
        });
        lastSearchedCitiesAdapter = new CityListAdapter(new CityListAdapter.CityItemListener() {
            @Override
            public void onFavoriteDelete(@NonNull City city) {
                userViewModel.removeFromFavoriteList(city);
            }

            @Override
            public void onFavoriteAdded(@NonNull City city) {
                userViewModel.addCityToFavoriteList(city);
            }

            @Override
            public void onItemClick(@NonNull City city) {
                startActivity(CityDetailActivity.newIntent(context, city));
            }
        });

        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(searchAdapter);

        binding.lastSearchesRecyclerView.setHasFixedSize(false);
        binding.lastSearchesRecyclerView.setNestedScrollingEnabled(false);
        binding.lastSearchesRecyclerView.setAdapter(lastSearchedCitiesAdapter);

        binding.search.setOnClickListener(this);
        binding.clearHistory.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(context, data);
                CharSequence name = place.getName();
                if (name != null) {
                    citiesViewModel.search(name.toString());
                    binding.search.setText(name);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(context, data);
                // TODO: Handle the error.
                Timber.i(status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
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
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    }
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Timber.e(e);
                }
                break;
            case R.id.clear_history:
                citiesViewModel.clearHistory();
                break;
        }
    }
}