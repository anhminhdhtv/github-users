package com.example.githubuser.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.githubuser.ui.components.extension.shimmerEffect

@Composable
fun ShimmerBox(with: Dp, height: Dp) {
    Box(
        Modifier
            .width(with)
            .height(height)
            .shimmerEffect()
    )
}