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
    implementation(project(":core:location:location-api"))
    implementation(libs.com.google.android.gms.play.services.location)
}
