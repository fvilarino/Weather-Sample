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
    implementation(libs.bundles.compose)
    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
