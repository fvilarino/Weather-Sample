plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.weather"
}

dependencies {
    implementation(project(":business:interactor:weather:api"))
    implementation(project(":core:time:api"))
    implementation(project(":utils"))

    // compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
}
