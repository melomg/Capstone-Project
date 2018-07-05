package com.projects.melih.wonderandwander.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.projects.melih.wonderandwander.model.User;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
@Dao
public interface UserDao {
    @Query("SELECT * from user where uId = :uId LIMIT 1")
    User getUser(String uId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("DELETE FROM user where uId = :uId")
    int deleteUser(String uId);

    @Query("DELETE FROM user")
    int deleteAll();
}