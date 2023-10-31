plugins {
    id("weathersample.android.feature")
    id("weathersample.android.library.compose")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.feature.favorites"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }
}

dependencies {
    implementation(project(":core:time:time-api"))
    implementation(project(":core:type:weather"))
    implementation(project(":domain:interactor:city:cityinteractor-api"))
    implementation(project(":domain:interactor:weather:weatherinteractor-api"))
    implementation(project(":ui:shared:composable:weather"))
    implementation(project(":ui:shared:weathericon"))
    implementation(libs.androidx.compose.material3.window.sizeclass)
    implementation(libs.dev.chrisbanes.haze)
}
