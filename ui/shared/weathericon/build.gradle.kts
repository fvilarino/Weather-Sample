plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.weathericon"
}

dependencies {
    implementation(project(":core:type:weather"))
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:lookup:api"))

    implementation(libs.androidx.compose.compose.bom)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
}
