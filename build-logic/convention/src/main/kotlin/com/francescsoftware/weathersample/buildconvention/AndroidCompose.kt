package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
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
            kotlinCompilerExtensionVersion = libs.findVersion("androidx-compose-compiler-version").get().toString()
        }
        dependencies {
            val bom = libs.findLibrary("androidx-compose-compose-bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findBundle("compose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.lifecycle.viewmodel.ktx").get())
            add("implementation", libs.findLibrary("androidx.hilt.hilt.navigation.compose").get())
            add("implementation", libs.findLibrary("androidx.navigation.navigation.compose").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.ui.tooling.preview").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.ui.test.manifest").get())
        }
    }
}
