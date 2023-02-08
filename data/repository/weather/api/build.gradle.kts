plugins {
    id("weathersample.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.weatherrepository.api"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }
}

dependencies {
    implementation(project(":core:type"))
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
}
