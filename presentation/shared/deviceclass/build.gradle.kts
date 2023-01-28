plugins {
    id("base-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.deviceclass"
}

dependencies {
    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.androidx.compose.runtime.runtime)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)

    debugImplementation(libs.androidx.compose.ui.ui.tooling)
}
