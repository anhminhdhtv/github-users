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
            _isRefreshing, _isHasFailure
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
            runCatching {
                removeAllUserUseCase.invoke(Unit)
            }.fold(
                onSuccess = {
                    _isRefreshing.value = false
                },
                onFailure = {
                    _isRefreshing.value = false
                    _isHasFailure.value = true
                }
            )
        }
        val users = _users.value
        val since = if (users.dataList.isEmpty()) 0 else users.dataList.last().id
        val config = FetchUserConfig(10, since)

        fetchUserUseCase.invoke(config).fold(
            onSuccess = {
                _isHasFailure.value = false
                _users.value = users.append(it)
            },
            onFailure = {
                if (users.dataList.isEmpty()) {
                    _isHasFailure.value = true
                }
                _isHasFailure.value = true
            }
        )

    }

    override fun navigateToDetail(username: String) {
        viewModelScope.launch {
            _usersEvent.emit(UsersEvent.NavigateToDetail(username))
        }
    }
}