package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        kotlinOptions {
            if (project.findProperty("enableComposeCompilerReports") == "true") {
                val composeReportsDir = "compose_reports"
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                )
            }
        }
        composeOptions {
            kotlinCompilerExtensionVersion = catalog.findVersion("androidx.compose.compiler.version").get().toString()
        }
        dependencies {
            val bom = catalog.findLibrary("androidx.compose.compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", catalog.findBundle("compose").get())
            add("implementation", catalog.findLibrary("androidx.compose.ui.ui.tooling.preview").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.tooling").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.test.manifest").get())
        }
    }
}
