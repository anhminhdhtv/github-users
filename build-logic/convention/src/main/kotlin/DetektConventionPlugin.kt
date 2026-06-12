import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

/**
 * Convention plugin for Detekt static analysis.
 *
 * Configures detekt with:
 * - Shared config from config/detekt/detekt.yml
 * - Compose-specific rules (io.nlopez.compose.rules)
 * - Warning-only severity (build never fails)
 * - Parallel execution for multi-module speed
 *
 * Auto-applied via AndroidLibraryConventionPlugin and AndroidFeatureConventionPlugin.
 * For :app module, apply manually: alias(libs.plugins.kta.detekt)
 *
 * Usage: ./gradlew detekt
 */
class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (pluginManager.hasPlugin("io.gitlab.arturbosch.detekt")) return@with
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<DetektExtension> {
                buildUponDefaultConfig = true
                allRules = false
                parallel = true
                autoCorrect = true
                config.setFrom(rootProject.files("config/detekt/detekt.yml"))
                ignoreFailures = true
                source.setFrom(files("src"))
            }

            tasks.withType<Detekt>().configureEach {
                reports {
                    html.required.set(true)
                    xml.required.set(true)
                    sarif.required.set(false)
                    md.required.set(false)
                }
            }

            val libs = extensions.getByType(
                VersionCatalogsExtension::class.java
            ).named("libs")

            dependencies {
                add(
                    "detektPlugins",
                    libs.findLibrary("detekt-compose-rules").get()
                )
                add(
                    "detektPlugins",
                    libs.findLibrary("detekt-formatting").get()
                )
            }
        }
    }
}