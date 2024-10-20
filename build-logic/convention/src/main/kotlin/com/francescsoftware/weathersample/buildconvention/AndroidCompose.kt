package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        experimentalProperties["android.experimental.enableScreenshotTest"] = true

        dependencies {
            val bom = catalog.findLibrary("androidx.compose.compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", catalog.findBundle("compose").get())
            add("implementation", catalog.findLibrary("androidx.compose.ui.ui.tooling.preview").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.tooling").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.test.manifest").get())
            add("screenshotTestImplementation", catalog.findLibrary("androidx.compose.ui.ui.tooling").get())
        }
    }

    jvmCompilerOptions {
        if (project.findProperty("enableComposeCompilerReports") == "true") {
            val composeReportsDir = "compose_reports"
            freeCompilerArgs.addAll(
                listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                ),
            )
        }
    }
}
