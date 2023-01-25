plugins {
    id("base-kotlin-library")
}

dependencies {
    testCompileOnly(libs.junit.junit)
    testImplementation(libs.org.junit.jupiter.junit.jupiter)
    testImplementation(libs.io.mockk)
}
