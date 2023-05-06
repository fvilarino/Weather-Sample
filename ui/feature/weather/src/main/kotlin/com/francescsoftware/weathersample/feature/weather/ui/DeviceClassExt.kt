package com.francescsoftware.weathersample.feature.weather.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.francescsoftware.weathersample.deviceclass.DeviceClass

internal val DeviceClass.isDualPane: Boolean
    @Composable get() = this == DeviceClass.Expanded &&
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
