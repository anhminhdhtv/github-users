plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.data)

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
