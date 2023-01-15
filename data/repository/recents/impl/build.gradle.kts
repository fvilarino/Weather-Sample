plugins {
    id("base-hilt-library")
}

android {
    namespace = "com.francescsoftware.weathersample.repository.recents.impl"
}

dependencies {
    implementation(project(":data:repository:recents:api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.runtime)
    kapt(libs.androidx.room.room.compiler)
}
