plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
}

android {
    namespace = "com.francescsoftware.weathersample.time.impl"
}

dependencies {
    implementation(project(":core:time:api"))
}
