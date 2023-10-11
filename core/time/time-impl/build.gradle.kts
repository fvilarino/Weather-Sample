plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
}

dependencies {
    implementation(project(":core:time:time-api"))
}
