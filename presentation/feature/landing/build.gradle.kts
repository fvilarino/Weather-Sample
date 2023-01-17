plugins {
    id("base-hilt-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.landing"
}

dependencies {

    implementation(project(":presentation:feature:city"))
    implementation(project(":presentation:feature:weather"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(libs.bundles.compose)
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
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
