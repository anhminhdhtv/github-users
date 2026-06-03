package com.example.githubuser.data.repo.local

import com.example.githubuser.data.cache.UserDb
import com.example.githubuser.data.local.IUserLocal
import com.example.githubusers.core.model.User

class FakeUserLocal : IUserLocal {
    var getAllUsersResult: List<UserDb> = emptyList()
    var getUserResult: UserDb? = null
    var getUserByNameResult: UserDb? = null

    var savedUser: User? = null
    var savedInDetail: Boolean? = null
    var removeAllUsersCalled = false

    override fun getAllUsers(): List<UserDb> {
        return getAllUsersResult
    }

    override fun getUser(id: Int): UserDb? {
        return getUserResult
    }

    override fun getUser(userName: String): UserDb? {
        return getUserByNameResult
    }

    override fun saveUser(user: User, inDetail: Boolean) {
        savedUser = user
        savedInDetail = inDetail
    }

    override fun removeAllUsers() {
        removeAllUsersCalled = true
    }
}
