package com.francescsoftware.weathersample.buildconvention

object Config {
    object Build {
        const val VersionName = "1.0.0"

        const val MinSdk = 23
        const val CompileSdk = 33
        const val TargetSdk = 33

        val JavaVersion = org.gradle.api.JavaVersion.VERSION_17
    }

    object CompilerArgs {
        val KotlinFreeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
        )
    }
}
