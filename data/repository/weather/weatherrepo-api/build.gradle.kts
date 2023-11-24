plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    api(project(":core:type:either"))
    api(project(":core:type:location"))
    testFixturesImplementation(project(":core:type:either"))
}
