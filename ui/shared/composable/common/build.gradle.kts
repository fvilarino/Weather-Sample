plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.composable.common"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
}

dependencies {
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:deviceclass"))
    implementation(project(":ui:shared:styles"))
}
