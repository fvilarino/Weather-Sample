package com.francescsoftware.weathersample.buildconvention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidLint(
    commonExtension: LibraryExtension,
) {
    commonExtension.apply {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies.add("lintChecks", libs.findLibrary("com.slack.lint.compose.compose.lint.checks").get())

        androidOptions {
            lint {
                quiet = false
                abortOnError = true
                checkDependencies = true
                ignoreTestSources = true
                warningsAsErrors = false
                lintConfig = file("${rootDir}/analysis/lint/lint-config.xml")
                htmlOutput = file("${project.buildDir}/reports/lint/lint.html")
            }
        }
    }
}

private fun Project.androidOptions(block: Action<LibraryExtension>): Unit =
    (this as ExtensionAware).extensions.configure("android", block)
