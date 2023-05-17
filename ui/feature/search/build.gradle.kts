plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.search"
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
    implementation(project(":core:time:api"))
    implementation(project(":core:type:weather"))
    implementation(project(":domain:interactor:city:api"))
    implementation(project(":domain:interactor:weather:api"))
    implementation(project(":ui:shared:composable:weather"))
    implementation(project(":ui:shared:format:weather"))

    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.hilt.hilt.navigation.compose)
    implementation(libs.androidx.navigation.navigation.compose)
}
