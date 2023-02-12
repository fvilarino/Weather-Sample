package com.francescsoftware.weathersample.buildconvention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

internal fun Project.configureAndroidLint(
    commonExtension: LibraryExtension,
) {
    commonExtension.apply {
        dependencies.add("lintChecks", "com.slack.lint.compose:compose-lint-checks:1.0.0")

        androidOptions {
            lint {
                quiet = false
                abortOnError = true
                checkDependencies = true
                ignoreTestSources = true
                warningsAsErrors = true
                lintConfig = file(rootDir.path + "/testing/lint/lint-config.xml")
                htmlOutput = file("${project.buildDir}/reports/lint/lint.html")
            }
        }
    }
}

private fun Project.androidOptions(block: Action<LibraryExtension>): Unit =
    (this as ExtensionAware).extensions.configure("android", block)
