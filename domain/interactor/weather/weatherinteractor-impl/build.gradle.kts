plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(projects.core.dispatcher)
    implementation(projects.core.time.timeApi)
    implementation(projects.core.type.weather)
    implementation(projects.data.repository.weather.weatherrepoApi)
    implementation(projects.domain.interactor.weather.weatherinteractorApi)
    testImplementation(testFixtures(projects.core.dispatcher))
    testImplementation(testFixtures(projects.core.time.timeApi))
    testImplementation(testFixtures(projects.data.repository.weather.weatherrepoApi))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
