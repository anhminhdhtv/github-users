package com.example.githubuser.feature.detail.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubuser.feature.detail.viewmodel.DetailEvent
import com.example.githubuser.feature.detail.viewmodel.DetailViewModel
import com.example.githubuser.feature.detail.viewmodel.DetailViewModelImpl
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    userName: String,
    onNavigateBack: () -> Unit
) {
    val detailViewModel: DetailViewModel = koinViewModel<DetailViewModelImpl>()
    val contentState = detailViewModel.contentState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        DetailScreenContent(
            modifier = Modifier.padding(padding),
            detailScreenUiState = contentState.value,
            onNavigationBack = { detailViewModel.navigationBack() }
        )
    }

    LaunchedEffect(Unit) {
        detailViewModel.init(userName)
        detailViewModel.detailEvent.collect { event ->
            when (event) {
                DetailEvent.NavigateBack -> onNavigateBack()
                is DetailEvent.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.errorMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}
