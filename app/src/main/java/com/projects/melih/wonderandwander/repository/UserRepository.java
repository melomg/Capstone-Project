package com.projects.melih.wonderandwander.repository;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.local.LocalFavoritesDataSource;
import com.projects.melih.wonderandwander.repository.local.LocalUserDataSource;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.widget.FavoriteWidgetProvider;

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

    @SuppressWarnings("WeakerAccess")
    @Inject
    public UserRepository(@NonNull Context applicationContext, @NonNull LocalUserDataSource localUserDataSource, @NonNull LocalFavoritesDataSource localFavoritesDataSource, @NonNull AppExecutors appExecutors) {
        this.context = applicationContext;
        this.localUserDataSource = localUserDataSource;
        this.localFavoritesDataSource = localFavoritesDataSource;
        this.appExecutors = appExecutors;
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
                    } else {
                        callback.onComplete(null, ErrorState.AUTHENTICATE_ERROR);
                    }
                });
            });
        }
    }

    public void removeCityFromFavoriteList(@NonNull final String favoritedCityId, @NonNull final DataCallback<String> callback) {
        if (!Utils.isNetworkConnected(context)) {
            callback.onComplete(null, ErrorState.NO_NETWORK);
        } else {
            appExecutors.diskIO().execute(() -> {
                final User user = localUserDataSource.getUser();
                // notify on the main thread
                appExecutors.mainThread().execute(() -> {
                    if (user != null) {
                        final DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("/user-favorites").child(user.getUId());
                        favoritesRef.child(favoritedCityId).removeValue();
                    }
                });
            });
        }
    }

    public void getLocalFavoriteList(@NonNull final DataCallback<List<FavoritedCity>> callback) {
        appExecutors.diskIO().execute(() -> {
            final List<FavoritedCity> favoritedCities = localFavoritesDataSource.getFavorites();
            appExecutors.mainThread().execute(() -> callback.onComplete(favoritedCities, ErrorState.NO_ERROR));
        });
    }

    public void refreshLocalFavoriteList(@Nullable final ArrayList<FavoritedCity> favoritedCities) {
        appExecutors.diskIO().execute(() -> {
            final User user = localUserDataSource.getUser();
            if (user != null) {
                if (CollectionUtils.isNotEmpty(favoritedCities)) {
                    for (FavoritedCity favoritedCity : favoritedCities) {
                        favoritedCity.userId = user.getUId();
                    }
                }
                localFavoritesDataSource.deleteAll();
                localFavoritesDataSource.insertAll(favoritedCities);
            }

            updateWidgets(favoritedCities);
        });
    }

    private void updateWidgets(@Nullable ArrayList<FavoritedCity> favoritedCities) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, FavoriteWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view_favorites);
        //Now update all widgets
        FavoriteWidgetProvider.updateFavoriteWidgets(context, appWidgetManager, favoritedCities, appWidgetIds);
    }
}