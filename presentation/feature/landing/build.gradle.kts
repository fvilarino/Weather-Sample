plugins {
    id("base-hilt-compose-library")
}

dependencies {

    implementation(project(":presentation:feature:city"))
    implementation(project(":presentation:feature:navigation:api"))
    implementation(project(":presentation:feature:weather"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material.material)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.app.compat)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
