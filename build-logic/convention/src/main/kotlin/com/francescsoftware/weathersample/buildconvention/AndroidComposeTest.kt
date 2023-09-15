package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidComposeTest(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            val bom = catalog.findLibrary("androidx-compose-compose-bom").get()
            add("androidTestImplementation", platform(bom))
            add("androidTestImplementation", catalog.findLibrary("androidx.compose.ui.ui.test.junit4").get())
        }
    }
}
