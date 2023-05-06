plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.weatherrepository.api"
}

dependencies {
    implementation(project(":core:type"))
}
