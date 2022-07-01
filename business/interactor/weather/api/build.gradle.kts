plugins {
    id("base-android-library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.weather.api"
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.androidx.core.core.ktx)
}
