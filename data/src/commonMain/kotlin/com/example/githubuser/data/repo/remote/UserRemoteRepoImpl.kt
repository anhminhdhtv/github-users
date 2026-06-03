package com.example.githubuser.data.repo.remote

import co.touchlab.kermit.Logger
import com.example.githubusers.core.dispatchers.QzDispatchers
import com.example.githubusers.core.model.User
import com.example.githubuser.data.network.IUserApi
import kotlinx.coroutines.withContext

class UserRemoteRepoImpl(
    private val userApi: IUserApi,
    private val dispatchers: QzDispatchers
) : IUserRemoteRepo {
    companion object {
        val TAG = UserRemoteRepoImpl::class.simpleName ?: ""
    }

    override suspend fun fetchUser(itemPerPage: Int, since: Int): Array<User>? =
        withContext(dispatchers.io) {
            runCatching {
                userApi.fetchUser(itemPerPage = itemPerPage, since = since)
                    .map { it.mapToUser() }.toTypedArray()
            }.getOrElse {
                Logger.d(tag = TAG, messageString = it.message ?: "")
                null
            }
        }

    override suspend fun fetchUserDetail(userName: String): User? = withContext(dispatchers.io) {
        runCatching {
            userApi.fetchUserDetail(userName = userName).mapToUser()

        }.getOrElse {
            Logger.d(tag = TAG, messageString = it.message ?: "")
            null
        }
    }
}