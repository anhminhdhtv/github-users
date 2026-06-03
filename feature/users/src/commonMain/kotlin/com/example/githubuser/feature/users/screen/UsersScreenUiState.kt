package com.example.githubuser.feature.users.screen

import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User

data class UsersScreenUiState(
    val users: ListDataStruct<User> = ListDataStruct(),
    val isRefreshing: Boolean = false,
    val isHasFailure: Boolean = false
) {
    companion object {
        val initial = UsersScreenUiState(
            users = ListDataStruct(),
            isRefreshing = false,
            isHasFailure = false
        )
    }
}