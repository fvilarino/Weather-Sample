package com.francescsoftware.weathersample.buildconvention

import com.squareup.anvil.plugin.AnvilExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

internal fun Project.configureDependencyInjection() {
    dependencies {
        add("implementation", catalog.findLibrary("com.google.dagger.dagger").get())
        add("implementation", project(":core:injection"))
        add("ksp", catalog.findLibrary("com.google.dagger.dagger.compiler").get())
    }
    anvilOptions {
        useKsp(
            contributesAndFactoryGeneration = true,
            componentMerging = true,
        )
    }
}

private fun Project.anvilOptions(block: AnvilExtension.() -> Unit) {
    configure<AnvilExtension> {
        block()
    }
}
