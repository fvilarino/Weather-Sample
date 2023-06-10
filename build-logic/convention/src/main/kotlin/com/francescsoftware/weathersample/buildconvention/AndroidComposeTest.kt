package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidComposeTest(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        dependencies {
            val bom = libs.findLibrary("androidx-compose-compose-bom").get()
            add("androidTestImplementation", platform(bom))
            add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.ui.test.junit4").get())
        }
    }
}
