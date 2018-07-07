package com.projects.melih.wonderandwander.repository.local;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.projects.melih.wonderandwander.model.User;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@Database(entities = {User.class}, version = 2)
public abstract class WonderAndWanderDatabase extends RoomDatabase {
    private static final Object lock = new Object();

    @SuppressLint("StaticFieldLeak")
    private static WonderAndWanderDatabase instance;

    public abstract UserDao userDao();

    public static WonderAndWanderDatabase getInstance(@NonNull Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        WonderAndWanderDatabase.class, "WonderAndWander.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }
    }
}