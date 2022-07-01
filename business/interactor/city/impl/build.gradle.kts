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
    implementation(project(":utils"))

    implementation(libs.androidx.core.core.ktx)
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    implementation(libs.com.jakewharton.timber)

    testImplementation(project(":testing"))
    testImplementation(libs.junit)

    testImplementation(libs.androidx.arch.core.core.testing)
    testImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
}
