package com.francescsoftware.weathersample.deviceclass

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

/** Describes the type of device the app is running on. */
enum class DeviceClass {
    Compact,
    Medium,
    Expanded;

    companion object {
        /**
         * Maps a [WindowSizeClass] object into a [DeviceClass] object.
         *
         * @param windowSizeClass - the [WindowSizeClass] to map to a [DeviceClass]
         * @return the [DeviceClass] mapping to the [windowSizeClass]
         */
        @Composable
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceClass =
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> Compact
                WindowWidthSizeClass.Medium -> Medium
                WindowWidthSizeClass.Expanded -> Expanded
                else -> Compact
            }
    }
}
