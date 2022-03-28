plugins {
    id("base-compose-library")
}

dependencies {
    implementation(project(":presentation:shared:assets"))

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material.material)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.com.google.android.material)
}
