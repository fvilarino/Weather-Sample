package com.francescsoftware.weathersample.buildconvention

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

internal fun Project.configureKotlinLibrary(
    javaPluginExtension: JavaPluginExtension,
) {
    javaPluginExtension.apply {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
