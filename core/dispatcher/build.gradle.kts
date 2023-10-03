plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
}

android {
    namespace = "com.francescsoftware.weathersample.core.dispather"
}

dependencies {
    implementation(libs.bundles.coroutines)
}
