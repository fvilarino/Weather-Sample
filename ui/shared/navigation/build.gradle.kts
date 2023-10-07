plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.navigation"
}

dependencies {
    implementation(libs.com.slack.circuit.circuit.runtime)
}
