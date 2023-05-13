plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.interactor.city.impl"
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type:either"))
    implementation(project(":data:repository:city:api"))
    implementation(project(":data:repository:favorite:api"))
    implementation(project(":data:repository:recents:api"))
    implementation(project(":domain:interactor:city:api"))
    testImplementation(project(":testing:fake"))
}
