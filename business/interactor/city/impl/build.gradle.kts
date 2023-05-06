plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.city.impl"
}

dependencies {
    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:city:api"))
    implementation(project(":data:repository:recents:api"))
    implementation(project(":utils"))

    testImplementation(project(":testing:mock"))
}
