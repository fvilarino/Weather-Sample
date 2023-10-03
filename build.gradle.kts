// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.com.android.tools.build.gradle)
        classpath(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
        classpath(libs.org.jetbrains.kotlin.kotlin.serialization.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.com.google.devtools.ksp.plugin)
    alias(libs.plugins.com.squareup.anvil)
    alias(libs.plugins.io.gitlab.arturbosch.detekt.plugin)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory.get())
}
