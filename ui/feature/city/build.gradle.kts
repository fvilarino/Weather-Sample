plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.feature.city"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs +
            "-opt-in=kotlinx.coroutines.FlowPreview" +
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api" +
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi" +
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":domain:interactor:city:api"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
    implementation(libs.androidx.navigation.navigation.compose)
}
