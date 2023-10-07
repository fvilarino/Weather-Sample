plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.francescsoftware.weathersample.core.network"
}

dependencies {
    implementation(project(":core:type:either"))

    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
}
