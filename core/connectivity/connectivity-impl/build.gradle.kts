plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
}

android {
    namespace = "com.francescsoftware.weathersample.core.connectivity.impl"
}
dependencies {
    implementation(project(":core:connectivity:connectivity-api"))
}
