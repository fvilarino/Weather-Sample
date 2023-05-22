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
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics",
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics",
                )
            }
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidx-compose-compiler-version").get().toString()
        }
        dependencies {
            val bom = libs.findLibrary("androidx-compose-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("implementation", libs.findBundle("compose").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.ui.tooling.preview").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.ui.tooling").get())
        }
    }
}
