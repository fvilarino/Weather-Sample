plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.weathericon"
}

dependencies {
    implementation(project(":ui:shared:assets"))
}
