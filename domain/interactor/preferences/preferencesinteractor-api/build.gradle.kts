plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    api(project(":domain:interactor:foundation"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
