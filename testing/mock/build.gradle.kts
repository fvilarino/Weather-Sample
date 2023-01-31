plugins {
    id("base-android-library")
}

android {
    namespace = "com.francescsoftware.weathersample.testing.mock"
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":utils"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
