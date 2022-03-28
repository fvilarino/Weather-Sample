plugins {
    id("base-hilt-library")
}

dependencies {
    implementation(project(":core:time:api"))
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
}
