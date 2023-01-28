plugins {
    id("base-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.styles"
}

dependencies {
    implementation(project(":presentation:shared:assets"))

    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.com.google.android.material)
}
