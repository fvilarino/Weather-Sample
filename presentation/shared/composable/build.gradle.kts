plugins {
    id("base-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.composable"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.composeExperimentalMaterial3
    }
}

dependencies {
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:deviceclass"))
    implementation(project(":presentation:shared:styles"))

    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.core.core.ktx)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
