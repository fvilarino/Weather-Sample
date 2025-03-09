plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    api(projects.domain.interactor.foundation)

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
