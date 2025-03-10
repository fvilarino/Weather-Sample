plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    api(projects.core.type.either)
    api(projects.core.type.location)
    implementation(libs.androidx.paging.paging.common.ktx)
    testFixturesImplementation(libs.app.cash.turbine.turbine)
}
