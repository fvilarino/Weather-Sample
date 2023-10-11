plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
}

android {
    namespace = "com.francescsoftware.weathersample.core.connectivity.impl"
}
dependencies {
    implementation(project(":core:connectivity:connectivity-api"))
    implementation(libs.bundles.coroutines)
}
