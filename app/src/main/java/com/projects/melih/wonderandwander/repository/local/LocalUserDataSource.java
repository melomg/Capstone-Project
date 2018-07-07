package com.projects.melih.wonderandwander.repository.local;

import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@Singleton
public class LocalUserDataSource implements UserDataSource {
    private final UserDao userDao;

    @Inject
    public LocalUserDataSource(@NonNull UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser() {
        return userDao.getUser();
    }

    @Override
    public long insertUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public int deleteUser(String uId) {
        return userDao.deleteUser(uId);
    }

    @Override
    public int deleteAll() {
        return userDao.deleteAll();
    }
}