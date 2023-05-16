plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.deviceclass"
}

dependencies {
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.androidx.compose.runtime.runtime)
}
