package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
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
    }

    jvmCompilerOptions {
        freeCompilerArgs.addAll(Config.CompilerArgs.KotlinFreeCompilerArgs)
        jvmTarget.set(jvmTargetVersion)
        allWarningsAsErrors.set(false)
    }
}

internal fun Project.jvmCompilerOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    configure<KotlinAndroidProjectExtension> {
        compilerOptions.apply(block)
    }
}
