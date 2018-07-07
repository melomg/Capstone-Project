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
import com.projects.melih.wonderandwander.databinding.FragmentUserTabBinding;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.base.BaseTabFragment;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class UserTabFragment extends BaseTabFragment {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private UserViewModel userViewModel;
    private Bundle savedInstanceState;

    public static UserTabFragment newInstance() {
        return new UserTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_tab, container, false);
        //noinspection ConstantConditions
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getIsLoginLiveData().observe(this, isLogin -> {
            if (savedInstanceState == null) {
                //TODO
            }
            BaseFragment childFragment;
            if ((isLogin != null) && isLogin) {
                childFragment = UserFragment.newInstance();
            } else {
                childFragment = LoginFragment.newInstance();
            }
            openChildFragment(R.id.container, childFragment, false);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }
}