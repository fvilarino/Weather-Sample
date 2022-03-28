plugins {
    id("base-hilt-compose-library")
}

dependencies {

    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:type"))
    implementation(project(":presentation:feature:navigation:api"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:lookup:api"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material.material)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.bundles.lifecycle)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
