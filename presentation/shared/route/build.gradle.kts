plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":presentation:shared:assets"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.savedstate)
}
