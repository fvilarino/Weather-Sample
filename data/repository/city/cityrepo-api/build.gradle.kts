plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    api(project(":core:type:either"))
    api(project(":core:type:location"))
    implementation(libs.androidx.paging.paging.common.ktx)
    testFixturesImplementation(libs.app.cash.turbine.turbine)
}
