plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
}

android {
    namespace = "com.francescsoftware.weathersample.core.connectivity.impl"
    buildFeatures {
        androidResources = false
    }
}

dependencies {
    implementation(projects.core.connectivity.connectivityApi)
    implementation(libs.bundles.coroutines)
}
