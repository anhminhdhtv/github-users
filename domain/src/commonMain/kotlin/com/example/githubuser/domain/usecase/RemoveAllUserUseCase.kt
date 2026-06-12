package com.example.githubuser.domain.usecase

import com.example.githubusers.core.usecase.UseCase
import com.example.githubuser.domain.manage.UserManager

class RemoveAllUserUseCase(val userManager: UserManager) : UseCase<Unit, Unit>() {
    override suspend fun invoke(input: Unit) {
        return userManager.removeALlUsers()
    }
}
