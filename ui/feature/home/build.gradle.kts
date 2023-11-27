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
    implementation(project(":core:connectivity:connectivity-api"))
    implementation(project(":domain:interactor:preferences:preferencesinteractor-api"))
    implementation(project(":ui:feature:favorites"))
    implementation(project(":ui:feature:search"))
    implementation(project(":ui:feature:settings"))
    implementation(project(":ui:shared:deeplink"))

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.androidx.core.core.splashscreen)
}
