plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.testing.mock"
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":utils"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
