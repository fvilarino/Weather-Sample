plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.testing.fake"
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
