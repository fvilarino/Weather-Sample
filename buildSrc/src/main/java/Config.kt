object Config {

    object Application {
        const val applicationId = "com.francescsoftware.weathersample"
    }

    object Compiler {
        const val jvmTarget = "11"
        val freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.time.ExperimentalTime",
        )
    }
}
