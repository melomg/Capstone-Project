package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.projects.melih.wonderandwander.common.SingleLiveEvent;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.UserRepository;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
public class UserViewModel extends ViewModel {
    private final SingleLiveEvent<Integer> errorLiveData;
    private final MutableLiveData<Boolean> loadingLiveData;
    private final MutableLiveData<User> userLiveData;
    private final UserRepository userRepository;
    private LiveData<List<FavoritedCity>> favoritesListeningLiveData;
    private MediatorLiveData<List<FavoritedCity>> favoritesLiveData;
    private MutableLiveData<Boolean> isLoginLiveData;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public UserViewModel(UserRepository userRepository, Context applicationContext) {
        this.userRepository = userRepository;
        errorLiveData = new SingleLiveEvent<>();
        loadingLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();
        favoritesLiveData = new MediatorLiveData<>();
        favoritesLiveData.addSource(userLiveData, user -> {
            if ((user != null) && (favoritesListeningLiveData == null)) {
                final String uId = user.getUId();
                favoritesListeningLiveData = userRepository.fetchFavoriteList(uId);
                if (!Utils.isNetworkConnected(applicationContext)) {
                    userRepository.getLocalFavoriteList(uId, (data, errorState) -> {
                        if (errorState == ErrorState.NO_ERROR) {
                            favoritesLiveData.setValue(data);
                        }
                    });
                }
                favoritesLiveData.addSource(favoritesListeningLiveData, favoritedCities -> favoritesLiveData.setValue(favoritedCities));
            }
        });
    }

    public SingleLiveEvent<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public MutableLiveData<Boolean> getIsLoginLiveData() {
        if (isLoginLiveData == null) {
            isLoginLiveData = new MutableLiveData<>();
            userRepository.getUser((user, errorState) -> isLoginLiveData.setValue(user != null));
        }
        return isLoginLiveData;
    }

    public MutableLiveData<User> getUserLiveData() {
        loadUser();
        return userLiveData;
    }

    public LiveData<List<FavoritedCity>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public void addCityToFavoriteList(@NonNull final City city) {
        userRepository.pushCityToFavoriteList(new FavoritedCity(city.getGeoHash(), city.getFullName(), city.getName(), city.getImageUrl()), (cityGeoHash, errorState) -> {
            errorLiveData.setValue(errorState);
            favoritesLiveData.setValue(favoritesLiveData.getValue());
        });
    }

    public void removeFromFavoriteList(@NonNull final City city) {
        userRepository.removeCityFromFavoriteList(city.getGeoHash(), (geoHash, errorState) -> {
            errorLiveData.setValue(errorState);
            favoritesLiveData.setValue(favoritesLiveData.getValue());
        });
    }

    public void saveUser(FirebaseUser firebaseUser) {
        loadingLiveData.setValue(true);
        if (firebaseUser == null) { // No user is signed in, deletes users
            userRepository.deleteAllUsers((isDeleteSuccessful, errorState) -> {
                //TODO think what to do when user delete is successful
                if (errorState == ErrorState.NO_ERROR) {
                    userLiveData.setValue(null);
                    isLoginLiveData.setValue(false);
                } else {
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
                    isLoginLiveData.setValue(true);
                } else {
                    errorLiveData.setValue(errorState);
                }
                loadingLiveData.setValue(false);
            });
        }
    }

    private void loadUser() {
        userRepository.getUser((user, errorState) -> {
            if (errorState == ErrorState.NO_ERROR) {
                userLiveData.setValue(user);
            }
        });
    }
}