plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.weather.api"
}

dependencies {
    implementation(project(":core:type"))
}
