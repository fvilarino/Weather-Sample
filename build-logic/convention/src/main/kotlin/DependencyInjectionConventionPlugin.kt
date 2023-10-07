import com.francescsoftware.weathersample.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DependencyInjectionConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.squareup.anvil")
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                add("implementation", catalog.findLibrary("com.google.dagger.dagger").get())
                add("implementation", project(":core:injection"))
                add("kapt", catalog.findLibrary("com.google.dagger.dagger.compiler").get())
            }
        }
    }
}
