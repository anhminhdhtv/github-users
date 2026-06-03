package com.example.githubusers.core.navigation

import androidx.navigation.NavGraphBuilder

interface FeatureNavigationRegistrar {
    fun NavGraphBuilder.registerComposable(callbacks: FeatureNavigationCallbacks)
}

interface FeatureNavigationCallbacks {
    fun navigateToDetail(name: String)
    fun navigateBack()
}

interface FeatureNavigationProvider {

    val featureNavigationRegistrar: FeatureNavigationRegistrar
}