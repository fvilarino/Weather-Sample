plugins {
    id("base-hilt-library")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.city.impl"
}

dependencies {
    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:city:api"))
    implementation(project(":data:repository:recents:api"))
    implementation(project(":utils"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
    testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
}
