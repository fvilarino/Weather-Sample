plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":utils"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.test)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.test.espresso.espresso.core)
}
