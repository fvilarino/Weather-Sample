plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.domain.interactor.weather.api"
}

dependencies {
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
}
