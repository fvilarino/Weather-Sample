plugins {
    id("weathersample.kotlin.library")
}

dependencies {
    implementation(project(":core:type:either"))
    implementation(libs.androidx.paging.paging.common.ktx)
}
