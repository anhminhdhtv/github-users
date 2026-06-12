package com.example.githubuser.feature.detail.screen

import com.example.githubusers.core.model.User

data class DetailScreenUiState(
    val userDetail: User? = null,
    val isLoading: Boolean = false
) {
    companion object {
        val initial = DetailScreenUiState(
            userDetail = null,
            isLoading = false
        )
    }
}