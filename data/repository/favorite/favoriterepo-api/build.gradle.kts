plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.favorite.api"
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
