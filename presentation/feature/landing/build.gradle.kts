plugins {
    id("base-hilt-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.landing"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs +
            Config.Compiler.composeExperimentalMaterial3 +
            Config.Compiler.composeExperimentalWindowSizeClass
    }
}

dependencies {

    implementation(project(":presentation:feature:city"))
    implementation(project(":presentation:feature:weather"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:deviceclass"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.material3.window.sizeclass)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.app.compat)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
