package com.example.githubuser.feature.users.screen

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
import com.example.githubuser.feature.users.viewmodel.UsersEvent
import com.example.githubuser.feature.users.viewmodel.UsersViewModel
import com.example.githubuser.feature.users.viewmodel.UsersViewModelImpl
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UsersRoute(
    modifier: Modifier = Modifier,
    onNavigationToDetail: (String) -> Unit,
) {
    val usersViewModel: UsersViewModel = koinViewModel<UsersViewModelImpl>()
    val contentState = usersViewModel.contentState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { padding ->
        UsersScreenContent(
            modifier = Modifier.padding(padding),
            contentState = contentState.value,
            onFetch = { page, isRefresh ->
                usersViewModel.fetch(page, isRefresh)
            },
            onItemClick = { name ->
                usersViewModel.navigateToDetail(name)
            }
        )
    }

    LaunchedEffect(Unit) {
        usersViewModel.usersEvent.collect { event ->
            when (event) {
                is UsersEvent.NavigateToDetail -> onNavigationToDetail(event.username)
                is UsersEvent.ShowErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        message = event.errorMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}