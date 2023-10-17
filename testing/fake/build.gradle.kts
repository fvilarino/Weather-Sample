plugins {
    id("weathersample.kotlin.library")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:time-api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
