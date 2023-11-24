plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
    id("weathersample.android.library.compose.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.search"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        )
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:location:location-api"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:weather"))
    implementation(project(":domain:interactor:city:cityinteractor-api"))
    implementation(project(":domain:interactor:weather:weatherinteractor-api"))
    implementation(project(":ui:shared:composable:weather"))
    implementation(project(":ui:shared:weathericon"))
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.com.google.accompanist.accompanist.permissions)
    implementation(libs.dev.chrisbanes.haze)
    implementation(libs.androidx.paging.paging.compose)
    implementation(libs.androidx.paging.paging.common.ktx)
}
