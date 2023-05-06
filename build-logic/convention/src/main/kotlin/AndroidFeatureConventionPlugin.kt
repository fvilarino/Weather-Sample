import com.android.build.gradle.LibraryExtension
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
                apply("weathersample.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
            dependencies {
                add("implementation", (project(":core:coroutines")))
                add("implementation", (project(":core:type")))
                add("implementation", (project(":ui:shared:assets")))
                add("implementation", (project(":ui:shared:composable")))
                add("implementation", (project(":ui:shared:deviceclass")))
                add("implementation", (project(":ui:shared:lookup:api")))
                add("implementation", (project(":ui:shared:mvi")))
                add("implementation", (project(":ui:shared:route")))
                add("implementation", (project(":ui:shared:styles")))
            }
        }
    }
}
