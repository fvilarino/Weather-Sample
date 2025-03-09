plugins {
    id("weathersample.kotlin.library")
    id("java-test-fixtures")
}

dependencies {
    api(projects.core.type.either)
    api(projects.core.type.location)
    testFixturesImplementation(projects.core.type.either)
}
