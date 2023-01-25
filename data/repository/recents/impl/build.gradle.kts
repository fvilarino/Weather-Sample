plugins {
    id("base-hilt-library")
}

android {
    namespace = "com.francescsoftware.weathersample.repository.recents.impl"
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(project(":data:repository:recents:api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.runtime)
    kapt(libs.androidx.room.room.compiler)

    testImplementation(libs.bundles.junit)
    testImplementation(libs.bundles.android.test)
    testImplementation(libs.androidx.room.room.testing)
    testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.app.cash.turbine.turbine)
    testImplementation(libs.org.robolectric.robolectric)
    testImplementation(libs.io.mockk)
}
