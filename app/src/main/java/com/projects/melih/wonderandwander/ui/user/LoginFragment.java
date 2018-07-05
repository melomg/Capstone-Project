package com.projects.melih.wonderandwander.ui.user;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentLoginBinding;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 01.07.2018
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final int RC_SIGN_IN = 123;

    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private final List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());
    private FragmentLoginBinding binding;
    private UserViewModel userViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        //noinspection ConstantConditions
        userViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(UserViewModel.class);
        userViewModel.getErrorLiveData().observe(this, errorCode -> {
            //TODO show error
        });
        userViewModel.getLoadingLiveData().observe(this, isLoading -> {
            //TODO show loading
        });
        userViewModel.getUserLiveData().observe(this, user -> navigationListener.replaceFragment(UserFragment.newInstance()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.login.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userViewModel.saveUser(user);
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise checks
                // response.getError().getErrorCode() and handle the error.
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response != null) {
                    final FirebaseUiException error = response.getError();
                    @ErrorCodes.Code int errorCode = (error == null) ? ErrorCodes.UNKNOWN_ERROR : error.getErrorCode();
                    switch (errorCode) {
                        case ErrorCodes.NO_NETWORK:
                            showSnackBar(binding.getRoot(), R.string.network_error);
                            break;
                        case ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED:
                            break;
                        case ErrorCodes.UNKNOWN_ERROR:
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Utils.await(v);
        switch (v.getId()) {
            case R.id.login:
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
                break;
        }
    }
}