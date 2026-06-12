package com.example.githubuser.data.local

import com.example.githubusers.core.model.User
import com.example.githubuser.data.cache.UserDb

interface IUserLocal {

    /**
     * Method to get all users from local cache.
     *
     * @return List of User
     */
    fun getAllUsers(): List<UserDb>

    /**
     * Method to get user by id from local cache.
     *
     * @param id The id of the user.
     *
     * @return User in success case, null otherwise
     */
    fun getUser(id: Int): UserDb?

    /**
     * Method to get user by username from local cache.
     *
     * @param userName The username of the user.
     *
     * @return User in success case, null otherwise
     */
    fun getUser(userName: String): UserDb?

    /**
     * Method to save user to local cache.
     *
     * @param user The user to save.
     * @param inDetail The flag to indicate if user is in detail.
     */
    fun saveUser(user: User, inDetail: Boolean = false)

    /**
     * Method to remove all users from local cache.
     */
    fun removeAllUsers()
}
