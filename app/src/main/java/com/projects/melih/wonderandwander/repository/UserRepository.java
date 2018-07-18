package com.projects.melih.wonderandwander.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.local.LocalFavoritesDataSource;
import com.projects.melih.wonderandwander.repository.local.LocalUserDataSource;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@Singleton
public class UserRepository {
    private final LocalUserDataSource localUserDataSource;
    private final LocalFavoritesDataSource localFavoritesDataSource;
    private final AppExecutors appExecutors;
    private final Context context;
    private final MediatorLiveData<List<FavoritedCity>> favoriteListLocalLiveData;
    @Nullable
    private LiveData<List<FavoritedCity>> favoriteListLiveData;

    @Inject
    public UserRepository(@NonNull Context applicationContext, @NonNull LocalUserDataSource localUserDataSource, @NonNull LocalFavoritesDataSource localFavoritesDataSource, @NonNull AppExecutors appExecutors) {
        this.context = applicationContext;
        this.localUserDataSource = localUserDataSource;
        this.localFavoritesDataSource = localFavoritesDataSource;
        this.appExecutors = appExecutors;
        this.favoriteListLocalLiveData = new MediatorLiveData<>();
    }

    public void getUser(@NonNull DataCallback<User> callback) {
        appExecutors.diskIO().execute(() -> {
            final User user = localUserDataSource.getUser();
            // notify on the main thread
            appExecutors.mainThread().execute(() -> {
                if (user != null) {
                    callback.onComplete(user, ErrorState.NO_ERROR);
                } else {
                    callback.onComplete(null, ErrorState.FAILED_GET_USER);
                }
            });
        });
    }

    public void saveUser(@NonNull final User user, @NonNull DataCallback<Boolean> callback) {
        appExecutors.diskIO().execute(() -> {
            final long rowId = localUserDataSource.insertUser(user);
            // notify on the main thread
            appExecutors.mainThread().execute(() -> {
                if (rowId != 0) {
                    callback.onComplete(true, ErrorState.NO_ERROR);
                } else {
                    callback.onComplete(null, ErrorState.FAILED_SAVE_USER);
                }
            });
        });
    }

    public void deleteUser(@NonNull final String uId, @NonNull DataCallback<Boolean> callback) {
        appExecutors.diskIO().execute(() -> {
            final int numberOfRows = localUserDataSource.deleteUser(uId);
            // notify on the main thread
            appExecutors.mainThread().execute(() -> {
                if (numberOfRows != 0) {
                    callback.onComplete(true, ErrorState.NO_ERROR);
                } else {
                    callback.onComplete(null, ErrorState.FAILED_DELETE_USER);
                }
            });
        });
    }

    public void deleteAllUsers(@NonNull DataCallback<Boolean> callback) {
        appExecutors.diskIO().execute(() -> {
            final int numberOfRows = localUserDataSource.deleteAll();
            // notify on the main thread
            appExecutors.mainThread().execute(() -> {
                if (numberOfRows != 0) {
                    callback.onComplete(true, ErrorState.NO_ERROR);
                } else {
                    callback.onComplete(null, ErrorState.FAILED_DELETE_USER);
                }
            });
        });
    }

    public LiveData<List<FavoritedCity>> fetchFavoriteList(String uId) {
        if (favoriteListLiveData == null) {
            final DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("/user-favorites").child(uId);
            FirebaseQueryLiveData favoritesQueryLiveData = new FirebaseQueryLiveData(favoritesRef);
            favoriteListLiveData = Transformations.map(favoritesQueryLiveData, new FavoritedCityListDeserializer());
            favoriteListLocalLiveData.addSource(favoriteListLiveData, favoritedCityList -> {
                favoriteListLocalLiveData.setValue(favoritedCityList);
                refreshLocalFavoriteList(uId, favoritedCityList);
            });
        }
        return favoriteListLiveData;
    }

    public void pushCityToFavoriteList(@NonNull final FavoritedCity favoritedCity, @NonNull final DataCallback<String> callback) {
        if (!Utils.isNetworkConnected(context)) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            appExecutors.diskIO().execute(() -> {
                final User user = localUserDataSource.getUser();
                // notify on the main thread
                appExecutors.mainThread().execute(() -> {
                    if (user != null) {
                        Map<String, Object> childUpdates = new HashMap<>();
                        final DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("/user-favorites").child(user.getUId());
                        Map<String, Object> postValues = favoritedCity.toMap();
                        childUpdates.put("/" + favoritedCity.getGeoHash(), postValues);
                        favoritesRef.updateChildren(childUpdates);

                        if (favoriteListLiveData != null) {
                            List<FavoritedCity> favoritedCityList = favoriteListLiveData.getValue();
                            if (favoritedCityList == null) {
                                favoritedCityList = new ArrayList<>();
                            }
                            favoritedCityList.add(favoritedCity);
                            favoriteListLocalLiveData.setValue(favoritedCityList);
                        }
                    }
                });
            });
        }
    }

    public void getLocalFavoriteList(@NonNull String uId, @NonNull final DataCallback<List<FavoritedCity>> callback) {
        appExecutors.diskIO().execute(() -> {
            final List<FavoritedCity> favoritedCities = localFavoritesDataSource.getFavoritesOfUser(uId);
            appExecutors.mainThread().execute(() -> callback.onComplete(favoritedCities, ErrorState.NO_ERROR));
        });
    }

    private void refreshLocalFavoriteList(@NonNull String uId, @Nullable final List<FavoritedCity> favoritedCities) {
        if (CollectionUtils.isNotEmpty(favoritedCities)) {
            for (FavoritedCity favoritedCity : favoritedCities) {
                favoritedCity.userId = uId;
            }
        }
        appExecutors.diskIO().execute(() -> {
            localFavoritesDataSource.deleteAll();
            localFavoritesDataSource.insertAll(favoritedCities);
        });
    }

    private static class FavoritedCityListDeserializer implements Function<DataSnapshot, List<FavoritedCity>> {
        @Override
        public List<FavoritedCity> apply(DataSnapshot dataSnapshot) {
            List<FavoritedCity> favoriteList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                FavoritedCity city = snapshot.getValue(FavoritedCity.class);
                favoriteList.add(city);
            }
            return favoriteList;
        }
    }
}