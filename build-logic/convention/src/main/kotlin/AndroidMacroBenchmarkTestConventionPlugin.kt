import com.android.build.gradle.TestExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidMacroBenchmark
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidMacroBenchmarkTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<TestExtension> {
                configureAndroidMacroBenchmark(this)
            }
        }
    }
}
