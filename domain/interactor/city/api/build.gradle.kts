plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.city.api"
}

dependencies {
    implementation(project(":core:type:either"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
