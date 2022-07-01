plugins {
    id("base-android-library")
}

android {
    namespace = "com.francescsoftware.weathersample.storage.city.api"
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
