plugins {
    id("base-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.composable"
}

dependencies {
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:styles"))

    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material3.material3)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.core.core.ktx)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
