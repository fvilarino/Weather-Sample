plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.city"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs +
            "-opt-in=kotlinx.coroutines.FlowPreview" +
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api" +
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    }
}

dependencies {
    implementation(project(":business:interactor:city:api"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
}
