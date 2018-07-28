package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentProfileBinding;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.citydetail.CityDetailActivity;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;
    private FavoriteListAdapter favoritesAdapter;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        //noinspection ConstantConditions
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, this::updateUI);
        userViewModel.getFavoritesLiveData().observe(this, favoritedCities -> {
            if (favoritedCities != null) {
                favoritesAdapter.submitFavoriteList(favoritedCities);
                if (CollectionUtils.isNotEmpty(favoritedCities)) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.emptyViewFavorites.setVisibility(View.GONE);
                } else {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.emptyViewFavorites.setVisibility(View.VISIBLE);
                }
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
        favoritesAdapter = new FavoriteListAdapter(new FavoriteListAdapter.FavoriteItemListener() {
            @Override
            public void onFavoriteDelete(@NonNull FavoritedCity favoritedCity) {
                userViewModel.removeFromFavoriteList(favoritedCity.getGeoHash());
            }

            @Override
            public void onItemClick(@NonNull FavoritedCity city) {
                Context context = getContext();
                if (context != null) {
                    startActivity(CityDetailActivity.newIntent(context, city));
                }
            }
        });
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setAdapter(favoritesAdapter);
        binding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.await(v);
        switch (v.getId()) {
            case R.id.logout:
                userViewModel.saveUser(null);
                break;
        }
    }

    private void updateUI(@Nullable User user) {
        if (user != null) {
            binding.setUser(user);
        }
    }
}