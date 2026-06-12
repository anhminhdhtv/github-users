package com.example.githubuser.domain.manage

import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User

interface UserManager {
    fun removeALlUsers()
    suspend fun fetchUser(itemPerPage: Int, since: Int): Result<ListDataStruct<User>>
    suspend fun fetchUserDetail(userName: String): Result<User>
}
