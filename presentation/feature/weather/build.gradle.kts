plugins {
    id("base-hilt-compose-library")
}

dependencies {

    implementation(project(":business:interactor:weather:api"))
    implementation(project(":core:type"))
    implementation(project(":core:time:api"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:lookup:api"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))
    implementation(project(":utils"))

    // compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)
    implementation(libs.androidx.navigation.navigation.compose)

    implementation(libs.androidx.core.core.ktx)

    implementation(libs.androidx.lifecycle.lifecycle.common.java8)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.hilt.navigation.compose)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
