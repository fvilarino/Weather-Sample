plugins {
    id("base-compose-library")
}

android {
    namespace = "com.francescsoftware.weathersample.shared.mvi"
}

dependencies {
    implementation(project(":core:coroutines"))
    implementation(project(":core:dispatcher"))

    implementation(libs.bundles.lifecycle)
    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
