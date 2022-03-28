plugins {
    id("base-hilt-library")
}

dependencies {
    implementation(libs.bundles.coroutines)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
}
