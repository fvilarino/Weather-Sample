plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":core:type:either"))
    implementation(libs.androidx.paging.paging.common.ktx)
}
