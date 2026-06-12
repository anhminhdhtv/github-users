package com.example.githubuser.feature.detail.viewmodel

import com.example.githubuser.feature.detail.screen.DetailScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.String

interface DetailViewModel {
    val contentState: StateFlow<DetailScreenUiState>
    val detailEvent: SharedFlow<DetailEvent>
    fun init(userName: String): Job
    fun navigationBack()
}