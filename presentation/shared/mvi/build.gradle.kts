plugins {
    id("base-compose-library")
}

dependencies {
    implementation(project(":core:dispatcher"))

    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.compose.runtime.runtime)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}