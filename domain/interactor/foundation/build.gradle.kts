plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.paging.common.ktx)
}
