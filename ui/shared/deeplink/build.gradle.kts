plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.deeplink"
}

dependencies {
    implementation(libs.com.slack.circuit.circuit.foundation)
    implementation(libs.com.slack.circuit.circuit.runtime)
}
