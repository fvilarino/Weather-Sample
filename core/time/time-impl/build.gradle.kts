plugins {
    id("weathersample.kotlin.library")
    id("weathersample.android.di")
}

dependencies {
    implementation(project(":core:time:time-api"))
}
