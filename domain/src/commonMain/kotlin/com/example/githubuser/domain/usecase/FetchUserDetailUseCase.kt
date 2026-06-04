package com.example.githubuser.domain.usecase

import com.example.githubusers.core.model.User
import com.example.githubusers.core.usecase.UseCase
import com.example.githubuser.domain.manage.UserManager

class FetchUserDetailUseCase(val userManager: UserManager) : UseCase<String, Result<User> >(){
    override suspend fun invoke(input: String): Result<User> {
        return userManager.fetchUserDetail(input)
    }
}