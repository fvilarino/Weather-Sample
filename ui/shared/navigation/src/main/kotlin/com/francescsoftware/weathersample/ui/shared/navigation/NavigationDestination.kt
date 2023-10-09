package com.francescsoftware.weathersample.ui.shared.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.slack.circuit.runtime.screen.Screen

/** Root navigation destinations */
interface NavigationDestination {
    /** Root screen for the destination */
    val rootScreen: Screen

    /** Label to display on the navigation bar */
    @get:StringRes val navItemLabel: Int

    /** Content description for the navigation bar */
    @get:StringRes val navItemContentDescription: Int

    /** Navigation icon for the navigation bar */
    val navItemIcon: ImageVector

    /** Label to dispay on the action bar */
    @Composable
    fun actionBarLabel(screen: Screen): String
}
