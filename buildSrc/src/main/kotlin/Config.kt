object Config {

    object Application {
        const val applicationId = "com.francescsoftware.weathersample"
    }

    object Compiler {
        const val jvmTarget = "11"
        val freeCompilerArgs = listOf(
            "-opt-in=kotlin.time.ExperimentalTime",
        )
        val serializationFreeCompileArgs = listOf(
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
        val composeFreeCompileArgs = listOf(
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
        )
        val flowFreeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.FlowPreview",
        )
    }
}
