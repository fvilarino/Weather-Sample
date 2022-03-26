// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.com.android.tools.build.gradle)
        classpath(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
        classpath(libs.org.jetbrains.kotlin.kotlin.serialization)
        classpath(libs.androidx.navigation.navigation.safe.args.gradle.plugin)
        classpath(libs.com.google.dagger.hilt.android.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.com.github.ben.manes.versions)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
