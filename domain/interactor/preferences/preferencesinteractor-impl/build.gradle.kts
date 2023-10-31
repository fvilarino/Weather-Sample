plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(project(":data:persistence:settings:settings-api"))
    implementation(project(":domain:interactor:preferences:preferencesinteractor-api"))
    testImplementation(testFixtures(project(":data:persistence:settings:settings-api")))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
