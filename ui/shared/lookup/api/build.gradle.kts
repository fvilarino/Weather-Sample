plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.lookup.api"
}

dependencies {
    implementation(libs.androidx.annotation)
}