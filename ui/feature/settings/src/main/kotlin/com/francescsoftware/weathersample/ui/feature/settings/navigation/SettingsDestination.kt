package com.francescsoftware.weathersample.ui.feature.settings.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.settings.presenter.SettingsScreen
import com.francescsoftware.weathersample.ui.shared.navigation.NavigationDestination
import com.slack.circuit.runtime.screen.Screen
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

object SettingsDestination : NavigationDestination {
    override val rootScreen: Screen
        get() = SettingsScreen

    override val navItemLabel: Int
        get() = assetsR.string.settings_bottom_nav

    override val navItemIcon: ImageVector
        get() = Icons.Default.Settings

    override val navItemContentDescription: Int
        get() = assetsR.string.content_description_settings_bottom_nav

    @Composable
    override fun actionBarLabel(screen: Screen): String = when (screen) {
        SettingsScreen -> stringResource(id = assetsR.string.app_name)
        else -> error("Unknown screen $screen")
    }
}
