package com.example.githubuser.feature.detail.viewmodel

sealed class DetailEvent {
    data object NavigateBack : DetailEvent()
    data class ShowErrorMessage(val errorMessage: String) : DetailEvent()
}