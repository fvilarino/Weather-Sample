// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.com.android.tools.build.gradle.plugin)
        classpath(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.squareup.anvil)
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    alias(libs.plugins.com.osacky.doctor)
}

doctor {
    // disabling as it complains even when present
    warnWhenNotUsingParallelGC = false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory.get())
}
