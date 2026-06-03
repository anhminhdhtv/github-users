plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
    alias(libs.plugins.githubuser.composeMultiplatform)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.domain)

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // coil dependencies to fetch images from url
            implementation(libs.coil.compose)
            implementation(libs.coil.network.common)
        }
    }
}