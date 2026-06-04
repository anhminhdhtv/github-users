package com.example.githubuser.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@OptIn(ExperimentalWasmDsl::class)
internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    jvmToolchain(17)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(libs.findLibrary("coroutines-core").get())
                implementation(libs.findLibrary("kermit").get())
            }

            androidMain {
                dependencies {
                    implementation(libs.findLibrary("coroutines-android").get())
                }

                jvmMain.dependencies {
                    // implementation(libs.findLibrary("coroutines-swing").get())
                }
            }
        }
    }
}