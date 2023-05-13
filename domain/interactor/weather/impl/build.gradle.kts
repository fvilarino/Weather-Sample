plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.weather.impl"
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
    implementation(project(":data:repository:weather:api"))
    implementation(project(":domain:interactor:weather:api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    testImplementation(project(":testing:fake"))
}
