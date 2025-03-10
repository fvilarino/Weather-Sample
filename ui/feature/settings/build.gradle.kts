plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.settings"
}

dependencies {
    implementation(projects.domain.interactor.preferences.preferencesinteractorApi)
}
