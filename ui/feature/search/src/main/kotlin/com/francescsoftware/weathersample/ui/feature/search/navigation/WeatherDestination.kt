package com.francescsoftware.weathersample.ui.feature.search.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.route.NavigationDestination

/** The Weather screen destination */
internal object WeatherDestination : NavigationDestination {
    /** @{inheritDoc} */
    override val title: String
        @Composable
        get() = stringResource(id = R.string.weather_label)

    /** @{inheritDoc} */
    override val icon: ImageVector
        @Composable
        get() = Icons.Default.ArrowBack

    /** @{inheritDoc} */
    override val iconContentDescription: String
        @Composable
        get() = stringResource(id = R.string.content_description_back)

    private const val routeRoot = "weather"
    private const val cityNameArg = "city"
    private const val countryArg = "country"
    private const val countryCodeArg = "countryCode"

    /** The route for the Weather screen */
    override val route: String = "$routeRoot/{$cityNameArg}/{$countryArg}/{$countryCodeArg}"

    /**
     * Gets the weather screen route for a [SelectedCity]
     *
     * @param selectedCity - the city to get the weather from
     * @return the route to navigate to
     */
    fun getRoute(selectedCity: SelectedCity): String =
        "$routeRoot/${selectedCity.name}/${selectedCity.country}/${selectedCity.countryCode}"

    /**
     * Retrieves the [SelectedCity] from the route payload
     *
     * @param savedStateHandle - the [SavedStateHandle] to load the [SelectedCity] from
     * @return the [SelectedCity] from the [SavedStateHandle]
     */
    fun getCity(savedStateHandle: SavedStateHandle): SelectedCity = SelectedCity(
        name = savedStateHandle.get<String>(cityNameArg).orEmpty(),
        country = savedStateHandle.get<String>(countryArg).orEmpty(),
        countryCode = savedStateHandle.get<String>(countryCodeArg).orEmpty(),
    )

    /** @{inheritDoc} */
    override fun isRoute(route: String?): Boolean = route?.startsWith(routeRoot) == true
}
