plugins {
    id("base-hilt-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.city"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.flowFreeCompilerArgs
    }
}

dependencies {

    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:type"))
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:lookup:api"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.bundles.lifecycle)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
