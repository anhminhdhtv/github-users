package com.example.githubuser.feature.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User
import com.example.githubuser.domain.usecase.FetchUserConfig
import com.example.githubuser.domain.usecase.FetchUserUseCase
import com.example.githubuser.domain.usecase.RemoveAllUserUseCase
import com.example.githubuser.feature.users.screen.UsersScreenUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsersViewModelImpl(
    private val fetchUserUseCase: FetchUserUseCase,
    private val removeAllUserUseCase: RemoveAllUserUseCase
) : ViewModel(), UsersViewModel {
    private val _users = MutableStateFlow<ListDataStruct<User>>(ListDataStruct())
    private val _isRefreshing = MutableStateFlow(false)
    private val _isHasFailure = MutableStateFlow(false)

    private val _usersEvent = MutableSharedFlow<UsersEvent>()
    override val contentState: StateFlow<UsersScreenUiState> =
        combine(
            _users,
            _isRefreshing,
            _isHasFailure
        ) { users, isRefreshing, isHasFailure ->
            UsersScreenUiState(
                users = users,
                isRefreshing = isRefreshing,
                isHasFailure = isHasFailure
            )
        }.distinctUntilChanged().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UsersScreenUiState.initial
        )
    override val usersEvent: SharedFlow<UsersEvent> = _usersEvent.asSharedFlow()

    override fun fetch(page: Int, isRefresh: Boolean) = viewModelScope.launch {
        if (isRefresh) {
            _isRefreshing.value = true
            _isHasFailure.value = false
        }
        val users = _users.value
        val since = if (isRefresh || users.dataList.isEmpty()) 0 else users.dataList.last().id
        val config = FetchUserConfig(10, since)

        fetchUserUseCase.invoke(config).fold(
            onSuccess = {
                if (isRefresh) {
                    runCatching {
                        removeAllUserUseCase.invoke(Unit)
                    }
                }
                _isRefreshing.value = false
                _isHasFailure.value = false
                _users.value = if (isRefresh) ListDataStruct<User>().append(it) else users.append(it)
            },
            onFailure = {
                _isRefreshing.value = false
                if (users.dataList.isEmpty()) {
                    _isHasFailure.value = true
                } else {
                    _usersEvent.emit(UsersEvent.ShowErrorMessage(it.toUserFriendlyMessage()))
                }
            }
        )
    }

    override fun navigateToDetail(username: String) {
        viewModelScope.launch {
            _usersEvent.emit(UsersEvent.NavigateToDetail(username))
        }
    }
}

private fun Throwable.toUserFriendlyMessage(): String {
    val message = this.message ?: ""
    return if (message.contains("resolve host", ignoreCase = true) ||
        message.contains("connect", ignoreCase = true) ||
        message.contains("network", ignoreCase = true) ||
        message.contains("timeout", ignoreCase = true) ||
        message.contains("address", ignoreCase = true)
    ) {
        "Connection error. Please check your internet connection and try again."
    } else {
        "An unexpected error occurred. Please try again later."
    }
}