plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.mvi"
}

dependencies {
    implementation(project(":core:coroutines"))
    implementation(project(":core:dispatcher"))

    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.compose)
}
