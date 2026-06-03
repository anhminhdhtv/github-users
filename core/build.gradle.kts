plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
    alias(libs.plugins.githubuser.composeMultiplatform)
}
kotlin {

    sourceSets {
        commonMain.dependencies {
            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.serialization)

            // Jetpack Compose integration
            implementation(libs.androidx.navigation.compose)

        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }
    }

}
compose.resources {
    publicResClass = true
    packageOfResClass = "com.example.githubusers.resources"
    generateResClass = auto
}