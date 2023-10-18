import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidTest
import com.francescsoftware.weathersample.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            extensions.configure<LibraryExtension> {
                configureAndroidTest(this)
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                dependencies {
                    add("androidTestImplementation", kotlin("test"))
                    add("testImplementation", kotlin("test"))
                    add("testImplementation", catalog.findBundle("test").get())
                    add("androidTestImplementation", catalog.findBundle("android.test").get())
                    add("testImplementation", project(":testing:fake"))
                }
            }
        }
    }
}
