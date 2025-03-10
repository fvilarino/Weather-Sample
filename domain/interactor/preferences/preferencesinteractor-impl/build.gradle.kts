plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("weathersample.kotlin.library.test")
}

dependencies {
    implementation(projects.data.persistence.settings.settingsApi)
    implementation(projects.domain.interactor.preferences.preferencesinteractorApi)
    testImplementation(testFixtures(projects.data.persistence.settings.settingsApi))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
