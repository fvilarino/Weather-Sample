plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    api(projects.core.type.either)
    api(projects.core.type.location)
    api(projects.domain.interactor.foundation)
    testFixturesImplementation(projects.core.type.either)

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.paging.common.ktx)
    testFixturesImplementation(libs.app.cash.turbine.turbine)
    testFixturesImplementation(libs.androidx.paging.paging.common.ktx)
}
