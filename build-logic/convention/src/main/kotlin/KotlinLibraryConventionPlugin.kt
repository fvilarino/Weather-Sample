import com.francescsoftware.weathersample.buildconvention.configureKotlinLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
            extensions.configure<JavaPluginExtension> {
                configureKotlinLibrary(this)
            }
        }
    }
}
