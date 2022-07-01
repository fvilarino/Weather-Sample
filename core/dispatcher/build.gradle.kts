plugins {
    id("base-hilt-library")
}

android {
    namespace = "com.francescsoftware.weathersample.dispather"
}

dependencies {
    implementation(libs.bundles.coroutines)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
}
