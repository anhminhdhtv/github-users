package com.example.githubuser.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.githubusers.core.navigation.DetailRouteData
import com.example.githubusers.core.navigation.FeatureNavigationCallbacks
import com.example.githubusers.core.navigation.FeatureNavigationProvider
import com.example.githubusers.core.navigation.UsersRouteData
import com.example.githubuser.feature.detail.nav.DetailNavigationProvider
import com.example.githubuser.feature.users.nav.UsersNavigationProvider

@Suppress("ParamsComparedByRef")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    // Discover navigation providers from dynamic feature modules
//    val navigationRegistrars = remember { FeatureNavigationRegistry.discoverFeatureNavigationProviders() }

    val navigationCallbacks = remember(navController) {
        object : FeatureNavigationCallbacks {
            override fun navigateToDetail(name: String) {
                navController.navigate(DetailRouteData(userName = name))
            }

            override fun navigateBack() {
                navController.navigateUp()
            }
        }
    }

    val providers: List<FeatureNavigationProvider> = listOf(
        UsersNavigationProvider(),
        DetailNavigationProvider(),
    )

    NavHost(
        navController = navController,
        startDestination = UsersRouteData,
        modifier = modifier,
    ) {
        providers.forEach { registrar ->
            with(registrar.featureNavigationRegistrar) {
                registerComposable(navigationCallbacks)
            }
        }
    }
}
