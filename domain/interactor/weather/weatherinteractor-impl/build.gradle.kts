plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
    implementation(project(":data:repository:weather:weatherrepo-api"))
    implementation(project(":domain:interactor:weather:weatherinteractor-api"))
    testImplementation(testFixtures(project(":core:dispatcher")))
    testImplementation(testFixtures(project(":core:time:time-api")))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
