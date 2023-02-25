plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
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
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.runtime)
    kapt(libs.androidx.room.room.compiler)

    testImplementation(libs.bundles.android.test)
    testImplementation(libs.androidx.room.room.testing)
    testImplementation(libs.org.robolectric.robolectric)
}
