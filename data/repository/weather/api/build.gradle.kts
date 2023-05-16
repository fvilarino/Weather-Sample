plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.weather.api"
}

dependencies {
    implementation(project(":core:type:either"))
}
