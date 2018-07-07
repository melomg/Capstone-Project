package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentProfileBinding;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        //noinspection ConstantConditions
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, this::updateUI);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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