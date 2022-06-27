plugins {
    id("base-compose-library")
}

dependencies {
    implementation(project(":core:dispatcher"))

    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.compose)
    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
