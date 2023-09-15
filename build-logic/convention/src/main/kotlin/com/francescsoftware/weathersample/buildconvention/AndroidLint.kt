package com.francescsoftware.weathersample.buildconvention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

internal fun Project.configureAndroidLint(
    commonExtension: LibraryExtension,
) {
    commonExtension.apply {
        dependencies.add("lintChecks", catalog.findLibrary("com.slack.lint.compose.compose.lint.checks").get())

        androidOptions {
            lint {
                quiet = false
                abortOnError = true
                checkDependencies = true
                ignoreTestSources = true
                warningsAsErrors = true
                lintConfig = file("${rootDir}/analysis/lint/lint-config.xml")
                htmlOutput = project.layout.buildDirectory.get().dir("reports/lint/lint.html").asFile
            }
        }
    }
}

private fun Project.androidOptions(block: Action<LibraryExtension>): Unit =
    (this as ExtensionAware).extensions.configure("android", block)
