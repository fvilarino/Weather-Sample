import com.francescsoftware.weathersample.buildconvention.configureDependencyInjection
import org.gradle.api.Plugin
import org.gradle.api.Project

class DependencyInjectionConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dev.zacsweers.anvil")
            }
            configureDependencyInjection()
        }
    }
}
