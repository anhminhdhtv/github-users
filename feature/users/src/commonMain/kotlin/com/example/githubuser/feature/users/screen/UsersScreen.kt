package com.example.githubuser.feature.users.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.githubuser.ui.components.CustomTopBar
import com.example.githubuser.ui.components.GenericLazyList
import com.example.githubuser.ui.components.UserItem
import com.example.githubuser.ui.components.UserItemShimmer
import com.example.githubuser.ui.components.rememberGenericLazyListState
import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.resources.Res
import com.example.githubusers.resources.github_users
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun UsersScreenContent(
    modifier: Modifier = Modifier,
    contentState: UsersScreenUiState,
    onFetch: (Int, Boolean) -> Unit = { _, _ -> },
    onItemClick: (String) -> Unit = {}
) {
    val listState = rememberGenericLazyListState(
        source = contentState.users,
        isHasFailure = contentState.isHasFailure,
        onFetch = { page ->
            onFetch(page, false)
        },
        onRefresh = {
            onFetch(0, true)
        }
    )

    Column(modifier = modifier.fillMaxSize()) {
        CustomTopBar(text = stringResource(Res.string.github_users))
        GenericLazyList(
            state = listState,
            itemKey = { user -> user.username },
            itemView = { _, user ->
                UserItem(
                    name = user.username,
                    link = user.htmlUrl,
                    imageLink = user.avatarUrl,
                    onClick = { onItemClick(user.username) }
                )
            },
            loadingIndicator = {
                UserItemShimmer()
            }
        )
    }
}

@Preview
@Composable
fun UsersScreenPreview() {
    UsersScreenContent(
        contentState = UsersScreenUiState(
            users = ListDataStruct(),
            isHasFailure = false
        )
    )
}

