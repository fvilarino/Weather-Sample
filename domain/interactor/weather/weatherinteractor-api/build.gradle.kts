plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    api(projects.domain.interactor.foundation)
    api(projects.core.type.location)
    implementation(projects.core.type.either)
    implementation(projects.core.type.weather)
}
