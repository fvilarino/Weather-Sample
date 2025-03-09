plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.favorites"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }
}

dependencies {
    implementation(projects.core.time.timeApi)
    implementation(projects.core.type.weather)
    implementation(projects.domain.interactor.city.cityinteractorApi)
    implementation(projects.domain.interactor.weather.weatherinteractorApi)
    implementation(projects.ui.shared.composable.weather)
    implementation(projects.ui.shared.deeplink)
    implementation(projects.ui.shared.weathericon)
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.dev.chrisbanes.haze)
}
