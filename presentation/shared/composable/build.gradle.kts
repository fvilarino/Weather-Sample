plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.test")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.composable"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
}

dependencies {
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:deviceclass"))
    implementation(project(":presentation:shared:styles"))

    implementation(libs.bundles.compose)
}
