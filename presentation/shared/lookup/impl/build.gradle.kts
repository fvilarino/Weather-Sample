plugins {
    id("base-hilt-library")
}

dependencies {
    implementation(project(":presentation:shared:lookup:api"))

    implementation(libs.androidx.annotation)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
}
