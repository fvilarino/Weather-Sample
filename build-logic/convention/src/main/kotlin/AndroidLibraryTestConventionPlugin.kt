import com.android.build.gradle.LibraryExtension
import com.francescsoftware.weathersample.buildconvention.configureAndroidTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                configureAndroidTest(this)
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("androidTestImplementation", kotlin("test"))
                    add("testImplementation", kotlin("test"))
                    add("testImplementation", libs.findBundle("test").get())
                    add("androidTestImplementation", libs.findBundle("android.test").get())
                    add("testImplementation", (project(":testing:fake")))
                }
            }
        }
    }
}
