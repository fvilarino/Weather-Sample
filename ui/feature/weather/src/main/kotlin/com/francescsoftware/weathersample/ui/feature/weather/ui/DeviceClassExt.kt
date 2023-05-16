package com.francescsoftware.weathersample.ui.feature.weather.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass

internal val DeviceClass.isDualPane: Boolean
    @Composable get() = this == DeviceClass.Expanded &&
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
