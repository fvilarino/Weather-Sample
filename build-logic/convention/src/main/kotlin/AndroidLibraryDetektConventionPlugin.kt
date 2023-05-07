import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureDetekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryDetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
                apply("com.android.library")
            }
            val extension = extensions.getByType<LibraryExtension>()
            configureDetekt(extension)
        }
    }
}
