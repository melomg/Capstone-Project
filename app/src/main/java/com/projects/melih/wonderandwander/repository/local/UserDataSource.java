package com.projects.melih.wonderandwander.repository.local;

import com.projects.melih.wonderandwander.model.User;

/**
 * Created by Melih Gültekin on 03.07.2018
 */
public interface UserDataSource {
    /**
     * Gets user from the data source.
     *
     * @param uId primary key
     * @return user from the data source.
     */
    User getUser(String uId);

    /**
     * Inserts the user in the data source, or, if it is an existing user, it updates it.
     *
     * @param user the user to be inserted or updated.
     * @return the new rowId
     */
    long insertUser(User user);

    /**
     * Deletes user from the data source.
     *
     * @param uId primary key
     * @return the number of rows removed
     */
    int deleteUser(String uId);

    /**
     * Deletes all users from the data source.
     *
     * @return the number of rows removed
     */
    int deleteAll();
}