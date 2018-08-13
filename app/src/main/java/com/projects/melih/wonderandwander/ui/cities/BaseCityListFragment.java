package com.projects.melih.wonderandwander.ui.cities;

import android.app.Activity;
import android.arch.core.util.Function;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentCityListBinding;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.ui.FirebaseDatabaseManager;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.citydetail.CityDetailActivity;
import com.projects.melih.wonderandwander.ui.user.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 26.06.2018
 */
public abstract class BaseCityListFragment extends BaseFragment implements View.OnClickListener {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private FragmentCityListBinding binding;
    private CitiesViewModel citiesViewModel;
    private UserViewModel userViewModel;
    private CityListAdapter lastSearchedCitiesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_list, container, false);
        //noinspection ConstantConditions
        citiesViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(CitiesViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);

        userViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                final DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("/user-favorites").child(user.getUId());
                new FirebaseDatabaseManager(BaseCityListFragment.this, favoritesRef, dataSnapshot -> {
                    ArrayList<FavoritedCity> favorites = new FavoritedCityListDeserializer().apply(dataSnapshot);
                    userViewModel.refreshFavoriteList(favorites);
                });
            }
        });
        citiesViewModel.getLoadingLiveData().observe(this, isLoading -> {
            if ((isLoading != null) && isLoading) {
                binding.progress.setVisibility(View.VISIBLE);
                binding.progress.show();
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.progress.hide();
            }
        });
        citiesViewModel.getCityLiveData().observe(this, city -> {
            List<FavoritedCity> favoritedCityList = userViewModel.getFavoritesLiveData().getValue();
            List<City> cities = new ArrayList<>();
            if (city != null) {
                cities.add(city);
            }
            updateSearchResultCity(city, favoritedCityList);
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
                updateSearchResultCity(citiesViewModel.getCityLiveData().getValue(), favoritedCities);
            }
        });
        userViewModel.getErrorLiveData().observe(this, errorCode -> {
            if (errorCode != null) {
                switch (errorCode) {
                    case ErrorState.NO_NETWORK:
                        showToast(R.string.network_error);
                        break;
                    case ErrorState.AUTHENTICATE_ERROR:
                        showToast(R.string.auth_error);
                        break;
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lastSearchedCitiesAdapter = new CityListAdapter(new CityListAdapter.CityItemListener() {
            @Override
            public void onFavoriteDelete(@NonNull City city) {
                userViewModel.removeFromFavoriteList(city.getGeoHash());
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

        binding.lastSearchesRecyclerView.setHasFixedSize(false);
        binding.lastSearchesRecyclerView.setNestedScrollingEnabled(false);
        binding.lastSearchesRecyclerView.setAdapter(lastSearchedCitiesAdapter);

        binding.searchResult.favoriteArea.setOnClickListener(this);
        binding.searchResult.getRoot().setOnClickListener(this);
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
        final City city = citiesViewModel.getCityLiveData().getValue();
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
            case R.id.search_result:
                if (city != null) {
                    startActivity(CityDetailActivity.newIntent(context, city));
                }
                break;
            case R.id.favorite_area:
                if (city != null) {
                    if (binding.searchResult.favoriteCheck.isChecked()) {
                        binding.searchResult.favoriteCheck.setChecked(false);
                        userViewModel.removeFromFavoriteList(city.getGeoHash());
                    } else {
                        binding.searchResult.favoriteCheck.setChecked(true);
                        userViewModel.addCityToFavoriteList(city);
                    }
                }
                break;
        }
    }

    private void updateSearchResultCity(@Nullable City city, @Nullable List<FavoritedCity> favoritedCityList) {
        if ((city != null) && CollectionUtils.isNotEmpty(favoritedCityList)) {
            city.setFavorited(false);
            for (FavoritedCity favoritedCity : favoritedCityList) {
                if (TextUtils.equals(city.getGeoHash(), favoritedCity.getGeoHash())) {
                    city.setFavorited(true);
                    break;
                }
            }
            binding.searchResult.setCity(city);
            binding.searchResult.favoriteCheck.setChecked(city.isFavorited());
            binding.searchResult.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.searchResult.getRoot().setVisibility(View.GONE);
        }
    }

    private static class FavoritedCityListDeserializer implements Function<DataSnapshot, ArrayList<FavoritedCity>> {
        @Override
        public ArrayList<FavoritedCity> apply(DataSnapshot dataSnapshot) {
            ArrayList<FavoritedCity> favoriteList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                FavoritedCity city = snapshot.getValue(FavoritedCity.class);
                favoriteList.add(city);
            }
            return favoriteList;
        }
    }
}