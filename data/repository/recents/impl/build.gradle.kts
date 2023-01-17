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

    testImplementation(libs.androidx.room.room.testing)
    testImplementation(libs.junit)
    testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.org.robolectric.robolectric)
    testImplementation(libs.androidx.test.ext.junit.ktx)
    testImplementation(libs.androidx.test.core.ktx)
    testImplementation(libs.app.cash.turbine.turbine)
}
