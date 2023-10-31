plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    testFixturesImplementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}
