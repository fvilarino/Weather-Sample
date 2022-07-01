plugins {
    id("base-android-library")
}

android {
    namespace = "com.francescsoftware.weathersample.presentation.route"
}

dependencies {
    implementation(project(":presentation:shared:assets"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.savedstate)
}
