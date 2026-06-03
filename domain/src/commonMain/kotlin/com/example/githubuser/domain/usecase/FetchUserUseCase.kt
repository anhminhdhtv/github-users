package com.example.githubuser.domain.usecase

import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User
import com.example.githubusers.core.usecase.UseCase
import com.example.githubuser.domain.manage.UserManager

data class FetchUserConfig(val itemPerPage: Int, val since: Int)

class FetchUserUseCase( val userManager: UserManager) :
    UseCase<FetchUserConfig, ListDataStruct<User>>() {
    override suspend fun invoke(input: FetchUserConfig): ListDataStruct<User> {
        return userManager.fetchUser(input.itemPerPage, input.since)
    }
}