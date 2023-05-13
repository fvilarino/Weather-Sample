plugins {
    id("weathersample.android.library")
}

android {
    namespace = "com.francescsoftware.weathersample.cityrepository.api"
}

dependencies {
    implementation(project(":core:type:either"))
}
