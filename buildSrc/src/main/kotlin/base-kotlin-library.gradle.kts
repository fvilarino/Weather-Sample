plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Config.Compiler.javaVersion
    targetCompatibility = Config.Compiler.javaVersion
}
