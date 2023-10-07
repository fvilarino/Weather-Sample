package com.francescsoftware.weathersample.ui.shared.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.slack.circuit.runtime.screen.Screen

interface NavigationDestination {
    val rootScreen: Screen
    @get:StringRes val navItemLabel: Int
    @get:StringRes val navItemContentDescription: Int
    val navItemIcon: ImageVector

    @Composable
    fun actionBarLabel(screen: Screen): String
}
