package com.example.githubuser.feature.users.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.githubusers.core.navigation.FeatureNavigationCallbacks
import com.example.githubusers.core.navigation.FeatureNavigationProvider
import com.example.githubusers.core.navigation.FeatureNavigationRegistrar
import com.example.githubusers.core.navigation.UsersRouteData
import com.example.githubuser.feature.users.screen.UsersRoute

class UsersNavigationProvider : FeatureNavigationProvider{
    override val featureNavigationRegistrar: FeatureNavigationRegistrar = object : FeatureNavigationRegistrar {
        override fun NavGraphBuilder.registerComposable(callbacks: FeatureNavigationCallbacks) {
            composable<UsersRouteData> { _ ->
                UsersRoute(
                    modifier = Modifier.fillMaxSize(),
                    onNavigationToDetail = callbacks::navigateToDetail
                )
            }
        }
    }
}