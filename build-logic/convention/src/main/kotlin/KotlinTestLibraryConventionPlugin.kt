import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named

class KotlinTestLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("testCompileOnly", libs.findLibrary("junit.junit").get())
                add("testImplementation", libs.findLibrary("com.willowtreeapps.assertk.assertk").get())
                add("testImplementation", libs.findLibrary("org.junit.jupiter.junit.jupiter").get())
                add("testImplementation", libs.findLibrary("io.mockk").get())
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
