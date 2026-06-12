package com.example.githubuser.data.repo.remote

import com.example.githubuser.data.network.IUserApi
import com.example.githubusers.core.dispatchers.QzDispatchers
import com.example.githubusers.core.model.User
import kotlinx.coroutines.withContext

class UserRemoteRepoImpl(
    private val userApi: IUserApi,
    private val dispatchers: QzDispatchers
) : IUserRemoteRepo {
    companion object {
        val TAG = UserRemoteRepoImpl::class.simpleName ?: ""
    }

    override suspend fun fetchUser(itemPerPage: Int, since: Int): Result<Array<User>?> =
        withContext(dispatchers.io) {
            runCatching {
                userApi.fetchUser(itemPerPage = itemPerPage, since = since)
                    .map { it.mapToUser() }.toTypedArray()
            }
        }

    override suspend fun fetchUserDetail(userName: String): Result<User?> =
        withContext(dispatchers.io) {
            runCatching {
                userApi.fetchUserDetail(userName = userName).mapToUser()
            }
        }
}
