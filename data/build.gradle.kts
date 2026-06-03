plugins {
    alias(libs.plugins.githubuser.kotlinMultiplatform)
    alias(libs.plugins.sqlDelight)
}

kotlin {

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
        androidMain.dependencies {
            implementation(libs.sqlDelight.driver.android)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.coil.network.anroid)
        }
        commonMain.dependencies {
            implementation(projects.core)

            // koin dependencies for DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // sqlDelight dependencies for local db
            implementation(libs.sqlDelight.driver.common)

            // Ktor
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.json)

            // Logging
            implementation(libs.kermit)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.coroutines.test)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelight.driver.ios)
            implementation(libs.ktor.client.darwin)
        }
    }
}


sqldelight {
    databases {
        create("MyDatabase1") {
            packageName.set("com.example.githubuser.data.cache")
        }
    }
}