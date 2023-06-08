import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("androidTestImplementation", kotlin("test"))
                    add("testImplementation", kotlin("test"))
                    add("testImplementation", libs.findBundle("test").get())
                    add("androidTestImplementation", libs.findBundle("android.test").get())
                    add("testImplementation", (project(":testing:fake")))
                }

                testOptions {
                    unitTests.all { test ->
                        test.useJUnitPlatform {
                            includeEngines("junit-jupiter")
                        }
                        test.testLogging {
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
    }
}
