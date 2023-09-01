package com.francescsoftware.weathersample.buildconvention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.javaVersion: JavaVersion
    get() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val jdkVersion = libs.findVersion("jdk.version").get().requiredVersion.toInt()
        return JavaVersion.toVersion(jdkVersion)
    }
