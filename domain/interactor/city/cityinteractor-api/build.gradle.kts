plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    implementation(project(":core:type:either"))
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
