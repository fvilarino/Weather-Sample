plugins {
    id("weathersample.kotlin.library")
    id("weathersample.dependency.injection")
    id("java-test-fixtures")
}

dependencies {
    implementation(libs.bundles.coroutines)
}
