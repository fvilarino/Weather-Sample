plugins {
    id("base-android-library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.city.api"
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
