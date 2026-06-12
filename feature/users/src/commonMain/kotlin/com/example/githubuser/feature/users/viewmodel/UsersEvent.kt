package com.example.githubuser.feature.users.viewmodel

sealed class UsersEvent {
    data class NavigateToDetail(val username: String) : UsersEvent()
    data class ShowErrorMessage(val errorMessage: String) : UsersEvent()
}