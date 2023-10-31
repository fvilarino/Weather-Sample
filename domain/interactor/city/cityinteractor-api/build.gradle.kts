plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":core:type:either"))
    api(project(":domain:interactor:foundation"))
    testFixturesImplementation(project(":core:type:either"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.paging.common.ktx)
    testFixturesImplementation(libs.app.cash.turbine.turbine)
    testFixturesImplementation(libs.androidx.paging.paging.common.ktx)
}
