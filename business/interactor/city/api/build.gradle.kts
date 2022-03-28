plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
