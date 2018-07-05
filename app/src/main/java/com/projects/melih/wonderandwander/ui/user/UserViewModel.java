package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.projects.melih.wonderandwander.common.SingleLiveEvent;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.UserRepository;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
public class UserViewModel extends ViewModel {
    private final SingleLiveEvent<Integer> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<User> userLiveData;
    private final UserRepository userRepository;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        errorLiveData = new SingleLiveEvent<>();
        loadingLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();
    }

    public SingleLiveEvent<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void saveUser(FirebaseUser firebaseUser) {
        loadingLiveData.setValue(true);
        if (firebaseUser == null) { // No user is signed in, deletes users
            userRepository.deleteAllUsers((isDeleteSuccessful, errorState) -> {
                //TODO think what to do when user delete is successful
                if (errorState != ErrorState.NO_ERROR) {
                    errorLiveData.setValue(errorState);
                }
                loadingLiveData.setValue(false);
            });
        } else {
            final Uri photoUrl = firebaseUser.getPhotoUrl();
            final User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(),
                    firebaseUser.getPhoneNumber(), (photoUrl == null) ? "" : photoUrl.toString(),
                    firebaseUser.isAnonymous(), firebaseUser.getUid());
            userRepository.saveUser(user, (isSaveSuccessful, errorState) -> {
                if (errorState == ErrorState.NO_ERROR) {
                    userLiveData.setValue(user);
                } else {
                    errorLiveData.setValue(errorState);
                }
                loadingLiveData.setValue(false);
            });
        }
    }
}