plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
    alias(libs.plugins.githubuser.composeMultiplatform)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
            api(project(":domain"))
            api(project(":ui-components"))

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Jetpack Compose integration
            implementation(libs.androidx.navigation.compose)
        }
    }
}