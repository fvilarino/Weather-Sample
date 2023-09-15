package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.variant.TestAndroidComponentsExtension
import com.android.build.gradle.TestExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidMacroBenchmark(
    commonExtension: TestExtension
) {
    commonExtension.apply {
        defaultConfig {
            targetSdk = Config.Build.TargetSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        buildFeatures {
            buildConfig = true
        }
        buildTypes {
            create("benchmark") {
                isDebuggable = true
                signingConfig = getByName("debug").signingConfig
                matchingFallbacks += listOf("release")
            }
        }

        targetProjectPath = ":app"
        experimentalProperties["android.experimental.self-instrumenting"] = true

        dependencies {
            add("implementation", catalog.findLibrary("androidx.test.ext.junit.ktx").get())
            add("implementation", catalog.findLibrary("androidx.test.espresso.espresso.core").get())
            add("implementation", catalog.findLibrary("androidx.test.uiautomator.uiautomator").get())
            add("implementation", catalog.findLibrary("androidx.benchmark.benchmark.macro.junit4").get())
        }

        extensions.configure<TestAndroidComponentsExtension> {
            beforeVariants(selector().all()) {
                it.enable = it.buildType == "benchmark"
            }
        }
    }
}
