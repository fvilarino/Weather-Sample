plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    api(project(":domain:interactor:foundation"))
    api(project(":core:type:location"))
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
}
