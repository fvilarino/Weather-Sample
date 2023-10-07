plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
}

dependencies {
    implementation(libs.bundles.coroutines)
}
