plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
}

dependencies {
    implementation(projects.core.time.timeApi)
}
