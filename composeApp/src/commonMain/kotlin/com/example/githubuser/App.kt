package com.example.githubuser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.githubuser.nav.AppNavHost
import com.example.githubuser.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() = AppTheme {
    AppNavHost(
        navController = rememberNavController(),
        modifier = Modifier.fillMaxSize()
    )
}