plugins {
    id("base-hilt-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.weather"
}

dependencies {
    implementation(project(":business:interactor:weather:api"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type"))
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:lookup:api"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))
    implementation(project(":utils"))

    // compose
    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.androidx.core.core.ktx)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
