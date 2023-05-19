plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.composable.weather"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
}

dependencies {
    implementation(project(":core:type:weather"))
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:composable:common"))
    implementation(project(":ui:shared:styles"))
    implementation(project(":ui:shared:weathericon"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.collections.immutable)
}
