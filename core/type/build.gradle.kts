plugins {
    id("base-kotlin-library")
}

dependencies {
    testCompileOnly(libs.junit)
    testImplementation(libs.org.junit.jupiter.junit.jupiter)
}
