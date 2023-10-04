plugins {
    id("weathersample.android.library")
    id("weathersample.android.library.compose")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.ui.shared.composable.common"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        )
    }
}

dependencies {
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:deviceclass"))
    implementation(project(":ui:shared:styles"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.collections.immutable)
    implementation(libs.androidx.compose.material3.window.sizeclass)
}
