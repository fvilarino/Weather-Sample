plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.test")
    id("weathersample.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.network"
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
}
