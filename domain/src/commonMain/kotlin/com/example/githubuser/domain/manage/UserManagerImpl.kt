package com.example.githubuser.domain.manage

import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User
import com.example.githubuser.data.repo.local.IUserLocalRepo
import com.example.githubuser.data.repo.remote.IUserRemoteRepo

class UserManagerImpl(
    private val userRemoteRepo: IUserRemoteRepo,
    private val userLocalRepo: IUserLocalRepo
) : UserManager {
    // flag to check if data exists in local cache
    private var isHasDbData: Boolean = false

    /**
     * Method to remove all users from local cache.
     */
    override fun removeALlUsers() {
        userLocalRepo.removeAllUsers()
    }

    /**
     * Method to get user list from local cache or remote server.
     *
     * @param itemPerPage The number of items per page.
     * @param since The id of the user to start fetching from.
     *
     * @return ListDataStruct of User in success case, empty list otherwise or throw ServerException
     */
    override suspend fun fetchUser(
        itemPerPage: Int,
        since: Int
    ): Result<ListDataStruct<User>> {
        // Fetch all users from local cache first time
        if (!isHasDbData) {
            userLocalRepo.getAllUsers().let {
                if (it.isNotEmpty()) {
                    // Set flag to true if data exists in local cache
                    isHasDbData = true
                    return Result.success(ListDataStruct(
                        dataCapacity = Long.MAX_VALUE,
                        dataList = it,
                        itemPerPage = itemPerPage
                    ))
                }
            }
        }

        // Fetch users from remote server if does not exist in local cache
        val result = userRemoteRepo.fetchUser(itemPerPage, since).getOrNull() ?: emptyArray()
        result.forEach {
            userLocalRepo.saveUser(it)
        }

        return Result.success(ListDataStruct(
            dataCapacity = Long.MAX_VALUE,
            dataList = result.toList(),
            itemPerPage = itemPerPage
        ))
    }


    /**
     * Method to get user detail from local cache or remote server.
     *
     * @param userName The username of the user.
     *
     * @return User in detail in success case, default User otherwise
     */
    override suspend fun fetchUserDetail(userName: String): Result<User> {
        val userLocal = userLocalRepo.getUser(userName = userName)
        if (userLocal != null && userLocal.isInDetail) {
            return Result.success(userLocal)
        }

        val remoteUser = userRemoteRepo.fetchUserDetail(userName).getOrNull()?.also {
            userLocalRepo.saveUser(it, true)
        } ?: return Result.failure(NoSuchElementException("User not found"))

        return Result.success(remoteUser)
    }
}