package com.example.githubuser

import androidx.compose.ui.window.ComposeUIViewController
import com.example.githubuser.di.initKoin

fun MainViewController() = run {
    initKoin {  }
    ComposeUIViewController { App() }
}