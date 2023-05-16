plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
}

android {
    namespace = "com.francescsoftware.weathersample.core.dispather"
}

dependencies {
    implementation(libs.bundles.coroutines)
}
