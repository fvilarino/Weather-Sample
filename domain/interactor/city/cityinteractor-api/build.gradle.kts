plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    implementation(project(":core:type:either"))
    api(project(":domain:interactor:foundation"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.paging.common.ktx)
}
