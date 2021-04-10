object Config {

    object Application {
        const val applicationId = "com.francescsoftware.weathersample"
    }

    object Compiler {
        const val jvmTarget = "1.8"
        val freeCompilerArgs = arrayOf(
            "-Xallow-result-return-type",
            "-Xuse-experimental=kotlin.time.ExperimentalTime"
        )
    }
}
