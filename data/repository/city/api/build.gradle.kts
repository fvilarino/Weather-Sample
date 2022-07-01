plugins {
    id("base-android-library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.cityrepository.api"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.serializationFreeCompileArgs
    }
}

dependencies {
    implementation(project(":core:type"))
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
}
