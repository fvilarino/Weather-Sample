import com.francescsoftware.weathersample.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.project

class KotlinTestLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testCompileOnly", catalog.findLibrary("junit.junit").get())
                add("testImplementation", catalog.findBundle("test").get())
                add("testImplementation", project(":testing:fake"))
            }
            tasks.named<Test>("test") {
                useJUnitPlatform()

                testLogging {
                    events("passed", "skipped", "failed")
                    showStandardStreams = true
                    showStackTraces = true
                    showCauses = true
                    exceptionFormat = TestExceptionFormat.FULL
                }
            }
        }
    }
}
