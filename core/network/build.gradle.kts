plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
    id("weathersample.android.library.test")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.core.network"
}

dependencies {
    implementation(project(":core:type:either"))

    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
}
