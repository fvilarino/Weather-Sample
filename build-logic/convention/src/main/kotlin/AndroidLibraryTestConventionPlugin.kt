import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    add("testImplementation", libs.findBundle("junit").get())
                    add("androidTestImplementation", libs.findBundle("android.test").get())
                    add("testImplementation", libs.findLibrary("org.jetbrains.kotlinx.kotlinx.coroutines.test").get())
                    add("testImplementation", libs.findLibrary("io.mockk").get())
                }

                testOptions {
                    unitTests.all { test ->
                        test.useJUnitPlatform {
                            includeEngines("junit-jupiter", "junit-vintage")
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
