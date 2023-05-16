plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.city.api"
}

dependencies {
    implementation(project(":core:type:either"))
}
