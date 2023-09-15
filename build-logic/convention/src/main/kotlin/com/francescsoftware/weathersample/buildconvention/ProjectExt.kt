package com.francescsoftware.weathersample.buildconvention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.catalog: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.javaVersion: JavaVersion
    get() {
        val jdkVersion = catalog.findVersion("jdk.version").get().requiredVersion.toInt()
        return JavaVersion.toVersion(jdkVersion)
    }
