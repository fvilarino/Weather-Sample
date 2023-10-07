plugins {
    id("weathersample.kotlin.library")
    id("weathersample.android.di")
}

dependencies {
    implementation(libs.bundles.coroutines)
}
