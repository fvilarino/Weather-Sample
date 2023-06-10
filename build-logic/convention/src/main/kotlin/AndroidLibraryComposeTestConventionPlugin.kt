import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidComposeTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidComposeTest(extension)
        }
    }
}
