plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.styles"
}

dependencies {
    implementation(project(":ui:shared:assets"))

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.com.google.android.material)
    implementation(libs.androidx.core.core.splashscreen)
}
