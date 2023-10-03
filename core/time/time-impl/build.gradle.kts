plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.core.time.impl"
}
dependencies {
    implementation(project(":core:time:time-api"))
}
