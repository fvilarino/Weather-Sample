package com.francescsoftware.weathersample.buildconvention

import com.android.build.gradle.LibraryExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt(
    commonExtension: LibraryExtension,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val version = libs.findVersion("io.gitlab.arturbosch.detekt.version").get().toString()
    commonExtension.apply {
        detektOptions {
            toolVersion = version
            allRules = true
            buildUponDefaultConfig = true
            source = files("$projectDir")
            config = files("$rootDir/analysis/detekt/detekt.yml")
            reportsDir = file("${rootProject.buildDir}/reports/detekt/")
            parallel = true
        }
        dependencies.add("detektPlugins", libs.findLibrary("io.gitlab.arturbosch.detekt.detekt.formatting").get())
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
