import com.francescsoftware.weathersample.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("implementation", catalog.findLibrary("com.google.dagger.hilt.android").get())
                add("ksp", catalog.findLibrary("com.google.dagger.hilt.android.compiler").get())
            }
        }
    }
}
