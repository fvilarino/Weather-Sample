plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.composable.weather"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
}

dependencies {
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:composable:common"))
    implementation(project(":ui:shared:styles"))
}
