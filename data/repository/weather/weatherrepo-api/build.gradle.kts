plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":core:type:either"))
    testFixturesImplementation(project(":core:type:either"))
}
