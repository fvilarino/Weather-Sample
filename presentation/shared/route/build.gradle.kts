plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.presentation.route"
}

dependencies {
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:assets"))

    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.runtime.runtime)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.savedstate)
}
