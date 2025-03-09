plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
}

dependencies {
    implementation(projects.core.dispatcher)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
