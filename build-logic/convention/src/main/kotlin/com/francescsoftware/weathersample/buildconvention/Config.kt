package com.francescsoftware.weathersample.buildconvention

object Config {
    object Build {
        const val VersionName = "1.0.0"

        const val MinSdk = 26
        const val CompileSdk = 34
        const val TargetSdk = 34
    }

    object CompilerArgs {
        val KotlinFreeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
        )
    }
}
