plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.favorites"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    }
}

dependencies {
    implementation(project(":core:time:api"))
    implementation(project(":core:type:weather"))
    implementation(project(":domain:interactor:city:api"))
    implementation(project(":domain:interactor:weather:api"))
    implementation(project(":ui:shared:composable:weather"))
    implementation(project(":ui:shared:format:weather"))

    // compose
    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
}
