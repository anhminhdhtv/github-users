package com.example.githubuser.feature.users.viewmodel

import com.example.githubuser.feature.users.screen.UsersScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UsersViewModel {
    val contentState: StateFlow<UsersScreenUiState>
    val usersEvent: SharedFlow<UsersEvent>
    fun fetch(page: Int, isRefresh: Boolean = false): Job
    fun navigateToDetail(username: String)
}