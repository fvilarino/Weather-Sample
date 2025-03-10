plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
}

android {
    namespace = "com.francescsoftware.weathersample.core.location.impl"
    buildFeatures {
        androidResources = false
    }
}

dependencies {
    implementation(projects.core.location.locationApi)
    implementation(libs.com.google.android.gms.play.services.location)
}
