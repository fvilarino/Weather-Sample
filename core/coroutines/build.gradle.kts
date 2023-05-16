plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
}

android {
    namespace = "com.francescsoftware.weathersample.core.coroutines"
}

dependencies {
    implementation(project(":core:dispatcher"))

    implementation(libs.bundles.coroutines)
}
