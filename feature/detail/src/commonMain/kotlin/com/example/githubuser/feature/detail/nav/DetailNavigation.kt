package com.example.githubuser.feature.detail.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.githubusers.core.navigation.DetailRouteData
import com.example.githubusers.core.navigation.FeatureNavigationCallbacks
import com.example.githubusers.core.navigation.FeatureNavigationProvider
import com.example.githubusers.core.navigation.FeatureNavigationRegistrar
import com.example.githubuser.feature.detail.screen.DetailRoute

class DetailNavigationProvider : FeatureNavigationProvider{
    override val featureNavigationRegistrar: FeatureNavigationRegistrar = object : FeatureNavigationRegistrar {
        override fun NavGraphBuilder.registerComposable(callbacks: FeatureNavigationCallbacks) {
            composable<DetailRouteData> { backStackEntry ->
                val detailRouteData = backStackEntry.toRoute<DetailRouteData>()
                DetailRoute(
                    modifier = Modifier.fillMaxSize(),
                    userName = detailRouteData.userName,
                    onNavigateBack = callbacks::navigateBack
                )
            }
        }
    }
}