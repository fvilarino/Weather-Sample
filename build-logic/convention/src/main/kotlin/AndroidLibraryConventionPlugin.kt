import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidLint
import com.francescsoftware.weathersample.buildconvention.configureKotlinAndroid
import com.francescsoftware.weathersample.buildconvention.disableUnnecessaryAndroidTests
import com.francescsoftware.weathersample.buildconvention.catalog
import com.francescsoftware.weathersample.buildconvention.configureDependencyInjection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
                apply("weathersample.android.library.detekt")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureAndroidLint(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("implementation", catalog.findLibrary("androidx.core.core.ktx").get())
                add("implementation", catalog.findLibrary("com.jakewharton.timber").get())
            }
        }
    }
}
