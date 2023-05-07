import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidLint
import com.francescsoftware.weathersample.buildconvention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

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
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("androidx.core.core.ktx").get())
                add("implementation", libs.findLibrary("org.jetbrains.kotlinx.kotlinx.collections.immutable").get())
                add("implementation", libs.findLibrary("com.jakewharton.timber").get())
            }
        }
    }
}
