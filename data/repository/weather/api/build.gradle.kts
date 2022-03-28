plugins {
    id("base-android-library")
    id("kotlinx-serialization")
}

dependencies {
    implementation(project(":core:type"))
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
}
