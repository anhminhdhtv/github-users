package com.example.githubuser.data.network

import com.example.githubuser.data.network.model.UserDto

interface IUserApi {

    /**
     * Method to fetch user list.
     *
     * @param itemPerPage The number of items per page.
     * @param since The id of the user to start fetching from.
     *
     * @return array of User
     */
    suspend fun fetchUser(itemPerPage: Int, since: Int): Array<UserDto>

    /**
     * Method to fetch user detail.
     *
     * @param userName The username of the user.
     *
     * @return User in detail
     */
    suspend fun fetchUserDetail(userName: String): UserDto
}
