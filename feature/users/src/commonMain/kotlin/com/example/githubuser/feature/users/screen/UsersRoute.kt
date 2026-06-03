package com.example.githubuser.feature.users.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    UsersScreenContent(
        modifier = modifier,
        contentState = contentState.value,
        onFetch = { page, isRefresh ->
            usersViewModel.fetch(page, isRefresh)
        },
        onItemClick = { name ->
            usersViewModel.navigateToDetail(name)
        }
    )

    LaunchedEffect(Unit) {
        usersViewModel.usersEvent.collect { event ->
            when (event) {
                is UsersEvent.NavigateToDetail -> onNavigationToDetail(event.username)
            }
        }
    }
}