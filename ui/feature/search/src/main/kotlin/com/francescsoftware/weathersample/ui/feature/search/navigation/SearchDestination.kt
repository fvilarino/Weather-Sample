package com.francescsoftware.weathersample.ui.feature.search.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.navigation.NavigationDestination
import com.slack.circuit.runtime.screen.Screen
import java.util.Locale
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

@Stable
object SearchDestination : NavigationDestination {
    override val rootScreen: Screen
        get() = SearchScreen

    override val navItemLabel: Int
        get() = assetsR.string.search_bottom_nav

    override val navItemIcon: ImageVector
        get() = Icons.Default.Search

    override val navItemContentDescription: Int
        get() = assetsR.string.content_description_search_bottom_nav

    @Composable
    override fun actionBarLabel(screen: Screen): String = when (screen) {
        SearchScreen -> stringResource(id = assetsR.string.app_name)
        is WeatherScreen ->
            screen.selectedCity.name
                .lowercase()
                .replaceFirstChar { it.titlecase(Locale.getDefault()) }

        else -> error("Unknown screen $screen")
    }
}
