plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.landing"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs +
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api" +
            "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi"
    }
}

dependencies {

    implementation(project(":ui:feature:city"))
    implementation(project(":ui:feature:weather"))

    // compose
    implementation(platform(libs.androidx.compose.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.core.core.splashscreen)

    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
}
