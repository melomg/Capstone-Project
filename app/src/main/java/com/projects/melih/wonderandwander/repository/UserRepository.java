package com.projects.melih.wonderandwander.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.model.User;
import com.projects.melih.wonderandwander.repository.local.LocalUserDataSource;
import com.projects.melih.wonderandwander.repository.remote.DataCallback;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@Singleton
public class UserRepository {
    private final LocalUserDataSource localUserDataSource;
    private final AppExecutors appExecutors;
    private final Context context;

    @Inject
    public UserRepository(@NonNull Context applicationContext, @NonNull LocalUserDataSource localUserDataSource, @NonNull AppExecutors appExecutors) {
        this.context = applicationContext;
        this.localUserDataSource = localUserDataSource;
        this.appExecutors = appExecutors;
    }

    public void getUser(@NonNull final String uId, @NonNull DataCallback<User> callback) {
        appExecutors.diskIO().execute(() -> {
            final User user = localUserDataSource.getUser(uId);
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
}