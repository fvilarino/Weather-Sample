package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = Config.Build.CompileSdk

        defaultConfig {
            minSdk = Config.Build.MinSdk
        }
        (this as? ApplicationExtension)?.defaultConfig {
            targetSdk = Config.Build.TargetSdk
        }
        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + Config.CompilerArgs.KotlinFreeCompilerArgs
            jvmTarget = javaVersion.toString()
            allWarningsAsErrors = true
        }
    }
}

internal fun CommonExtension<*, *, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
