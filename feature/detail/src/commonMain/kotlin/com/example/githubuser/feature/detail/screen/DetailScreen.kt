package com.example.githubuser.feature.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.githubuser.ui.components.CustomTopBar
import com.example.githubuser.ui.components.ShimmerBox
import com.example.githubuser.ui.components.UserItem
import com.example.githubuser.ui.components.UserItemShimmer
import com.example.githubusers.core.model.User
import com.example.githubusers.resources.Res
import com.example.githubusers.resources.blog
import com.example.githubusers.resources.follower
import com.example.githubusers.resources.following
import com.example.githubusers.resources.user_details
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreenContent(
    modifier: Modifier,
    detailScreenUiState: DetailScreenUiState,
    onNavigationBack: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        CustomTopBar(text = stringResource(Res.string.user_details)) {
            onNavigationBack()
        }
        if (detailScreenUiState.isLoading) {
            LoadingState()
        } else {
            LoadedState(detailScreenUiState.userDetail)
        }
    }
}

@Composable
private fun LoadingState() {
    UserItemShimmer(1)
    UserStatsRow(
        followersCount = "",
        followingCount = "",
        isLoading = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    BlogSection(blogUrl = "", isLoading = true)
}

@Composable
private fun LoadedState(userDetail: User?) {
    val userDetail = userDetail ?: return
    UserItem(
        name = userDetail.username,
        link = userDetail.htmlUrl,
        imageLink = userDetail.avatarUrl,
    )
    UserStatsRow(
        followersCount = userDetail.followers.toString(),
        followingCount = userDetail.following.toString()
    )
    Spacer(modifier = Modifier.height(16.dp))
    BlogSection(blogUrl = userDetail.blog)
}

@Composable
private fun UserStatsRow(
    modifier: Modifier = Modifier,
    followersCount: String,
    followingCount: String,
    isLoading: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoItem(
            icon = Icons.Filled.Person,
            count = followersCount,
            label = stringResource(Res.string.follower),
            isLoading = isLoading
        )
        InfoItem(
            icon = Icons.Filled.Favorite,
            count = followingCount,
            label = stringResource(Res.string.following),
            isLoading = isLoading
        )
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    count: String,
    label: String,
    isLoading: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }

        InfoText(text = count, style = MaterialTheme.typography.bodyLarge, isLoading = isLoading)
        InfoText(text = label, style = MaterialTheme.typography.bodyMedium, isLoading = isLoading)
    }
}

@Composable
private fun InfoText(
    text: String,
    style: TextStyle,
    isLoading: Boolean
) {
    if (isLoading) {
        ShimmerBox(64.dp, 16.dp)
    } else {
        Text(
            text = text,
            style = style,
            fontWeight = FontWeight.Bold.takeIf { style == MaterialTheme.typography.bodyLarge }
        )
    }
}

@Composable
private fun BlogSection(
    modifier: Modifier = Modifier,
    blogUrl: String,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.blog),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        if (isLoading) {
            ShimmerBox(200.dp, 16.dp)
        } else {
            Text(
                text = blogUrl,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
fun DetailScreenContentPreview() {
    DetailScreenContent(
        modifier = Modifier,
        detailScreenUiState = DetailScreenUiState(
            isLoading = false,
            userDetail = User(
                username = "John Doe",
                avatarUrl = "",
                htmlUrl = "https://github.com/johndoe",
                blog = "https://johndoe.com",
                followers = 100,
                following = 50
            )
        )
    )
}
