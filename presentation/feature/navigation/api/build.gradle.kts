plugins {
    id("base-compose-library")
}

dependencies {

    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
