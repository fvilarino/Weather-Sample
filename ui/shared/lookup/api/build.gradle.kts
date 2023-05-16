plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.lookup.api"
}

dependencies {
    implementation(libs.androidx.annotation)
}
