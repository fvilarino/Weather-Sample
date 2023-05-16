plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.recents.api"
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
