plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.route"
}

dependencies {
    implementation(project(":ui:shared:assets"))

    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.navigation.navigation.compose)
}
