package com.example.githubuser.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.core.model.User
import com.example.githubuser.domain.usecase.FetchUserDetailUseCase
import com.example.githubuser.feature.detail.screen.DetailScreenUiState
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

class DetailViewModelImpl(val fetchUserDetailUseCase: FetchUserDetailUseCase) :
    ViewModel(),
    DetailViewModel {
    private val _userDetail = MutableStateFlow<User?>(User())
    private val _isLoading = MutableStateFlow(false)
    private val _detailEvent = MutableSharedFlow<DetailEvent>()

    override val contentState: StateFlow<DetailScreenUiState> = combine(
        _userDetail,
        _isLoading
    ) { userDetail, isLoading ->
        DetailScreenUiState(
            userDetail = userDetail,
            isLoading = isLoading
        )
    }.distinctUntilChanged().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailScreenUiState.initial
    )

    override val detailEvent: SharedFlow<DetailEvent> = _detailEvent.asSharedFlow()

    override fun init(userName: String) = viewModelScope.launch {
        if (userName.isBlank()) {
            _detailEvent.emit(DetailEvent.ShowErrorMessage("Invalid user name"))
            return@launch
        }
        _isLoading.value = true
        runCatching {
            fetchUserDetailUseCase.invoke(userName)
        }.fold(
            onSuccess = {
                _userDetail.value = it.getOrNull()
                _isLoading.value = false
            },
            onFailure = {
                _isLoading.value = false
                _detailEvent.emit(DetailEvent.ShowErrorMessage(it.message ?: "Something went wrong"))
            }
        )
    }

    override fun navigationBack() {
        viewModelScope.launch {
            _detailEvent.emit(DetailEvent.NavigateBack)
        }
    }
}