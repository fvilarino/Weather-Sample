plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.format.weather"
}

dependencies {
    implementation(project(":core:type:weather"))
    implementation(project(":ui:shared:lookup:api"))
    implementation(project(":ui:shared:assets"))
}
