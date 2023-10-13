plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
    api(project(":domain:interactor:foundation"))
}
