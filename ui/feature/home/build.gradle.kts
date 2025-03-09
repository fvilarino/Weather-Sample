plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.home"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
        )
    }
}

dependencies {
    implementation(projects.core.connectivity.connectivityApi)
    implementation(projects.domain.interactor.preferences.preferencesinteractorApi)
    implementation(projects.ui.feature.favorites)
    implementation(projects.ui.feature.search)
    implementation(projects.ui.feature.settings)
    implementation(projects.ui.shared.deeplink)

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.androidx.core.core.splashscreen)
}
