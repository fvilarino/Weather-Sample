import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("weathersample.android.library")
                apply("weathersample.android.di")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
            dependencies {
                add("implementation", project(":core:coroutines"))
                add("implementation", project(":core:type:either"))
                add("implementation", project(":ui:shared:assets"))
                add("implementation", project(":ui:shared:composable:common"))
                add("implementation", project(":ui:shared:deviceclass"))
                add("implementation", project(":ui:shared:styles"))
                add("ksp", catalog.findLibrary("com.slack.circuit.circuit.codegen").get())
                add("implementation", catalog.findBundle("circuit").get())
                add("implementation", catalog.findLibrary("org.jetbrains.kotlinx.kotlinx.collections.immutable").get())
            }
        }
    }
}
