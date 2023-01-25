plugins {
    id("base-hilt-library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.weather.impl"
}

dependencies {
    implementation(project(":business:interactor:weather:api"))
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:weather:api"))
    implementation(project(":utils"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.com.jakewharton.timber)

    testImplementation(project(":testing"))
    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
    testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
}
