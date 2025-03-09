plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.composable.weather"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        )
    }
}

dependencies {
    implementation(projects.core.type.weather)
    implementation(projects.ui.shared.assets)
    implementation(projects.ui.shared.composable.common)
    implementation(projects.ui.shared.styles)
    implementation(projects.ui.shared.weathericon)

    implementation(libs.org.jetbrains.kotlinx.kotlinx.collections.immutable)
}
