plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":core:type"))

    implementation(libs.androidx.core.core.ktx)
}
