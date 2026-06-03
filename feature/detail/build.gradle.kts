plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
    alias(libs.plugins.githubuser.composeMultiplatform)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.domain)
            api(projects.uiComponents)

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Jetpack Compose integration
            implementation(libs.androidx.navigation.compose)
        }
    }
}

//dependencies {
//    implementation(libs.androidx.foundation.layout.android)
//    debugImplementation(compose.uiTooling)
//}
