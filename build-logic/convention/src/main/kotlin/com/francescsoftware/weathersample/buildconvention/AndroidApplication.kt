package com.francescsoftware.weathersample.buildconvention

import com.android.build.api.dsl.ApplicationExtension

internal fun configureAndroidApplication(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.apply {
        defaultConfig {
            versionCode = Config.Build.VersionCode
            versionName = Config.Build.VersionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}
