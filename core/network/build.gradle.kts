plugins {
    id("base-hilt-library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.network"
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
