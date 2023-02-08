package com.francescsoftware.weathersample.buildconvention

import org.gradle.api.plugins.JavaPluginExtension

internal fun configureKotlinLibrary(
    javaPluginExtension: JavaPluginExtension,
) {
    javaPluginExtension.apply {
        sourceCompatibility = Config.Build.JavaVersion
        targetCompatibility = Config.Build.JavaVersion
    }
}
