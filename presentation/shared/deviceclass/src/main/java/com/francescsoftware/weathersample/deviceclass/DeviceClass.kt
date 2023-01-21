package com.francescsoftware.weathersample.deviceclass

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class DeviceClass {
    Compact,
    Medium,
    Expanded;

    companion object {
        @Composable
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceClass {
            val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            return if (isLandscape) {
                when (windowSizeClass.heightSizeClass) {
                    WindowHeightSizeClass.Compact -> Compact
                    WindowHeightSizeClass.Medium -> Medium
                    WindowHeightSizeClass.Expanded -> Expanded
                    else -> Compact
                }
            } else {
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> Compact
                    WindowWidthSizeClass.Medium -> Medium
                    WindowWidthSizeClass.Expanded -> Expanded
                    else -> Compact
                }
            }
        }
    }
}
