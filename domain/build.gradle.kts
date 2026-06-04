plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
            api(project(":data"))

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.android.junit)
            implementation(libs.coroutines.test)
            implementation(libs.mockk)
        }
    }
}
