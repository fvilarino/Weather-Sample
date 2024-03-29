package com.francescsoftware.weathersample.buildconvention

import com.android.build.gradle.LibraryExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt(
    commonExtension: LibraryExtension,
) {
    val version = catalog.findVersion("io.gitlab.arturbosch.detekt.version").get().toString()
    commonExtension.apply {
        detektOptions {
            toolVersion = version
            allRules = true
            buildUponDefaultConfig = true
            source.from(files("$projectDir"))
            config.from(files("$rootDir/analysis/detekt/detekt.yml"))
            reportsDir = project.layout.buildDirectory.get().dir("reports/detekt").asFile
            parallel = true
        }
        dependencies.add("detektPlugins", catalog.findLibrary("io.gitlab.arturbosch.detekt.detekt.formatting").get())
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required.set(false)
            html.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
    }
}

private fun Project.detektOptions(block: Action<DetektExtension>): Unit =
    (this as ExtensionAware).extensions.configure("detekt", block)
