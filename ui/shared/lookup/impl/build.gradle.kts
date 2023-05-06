plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
}

android {
    namespace = "com.francescsoftware.weathersample.lookup.impl"
}

dependencies {
    implementation(project(":ui:shared:lookup:api"))

    implementation(libs.androidx.annotation)
}
