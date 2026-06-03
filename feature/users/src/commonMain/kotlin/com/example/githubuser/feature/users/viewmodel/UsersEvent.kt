package com.example.githubuser.feature.users.viewmodel

sealed class UsersEvent {
    data class NavigateToDetail(val username: String): UsersEvent()
}