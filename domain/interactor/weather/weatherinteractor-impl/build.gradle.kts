plugins {
    id("weathersample.kotlin.library")
    id("weathersample.android.di")
    id("weathersample.kotlin.test.library")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
    implementation(project(":data:repository:weather:weatherrepo-api"))
    implementation(project(":domain:interactor:weather:weatherinteractor-api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
