package com.francescsoftware.weathersample.presentation.route

import androidx.lifecycle.SavedStateHandle

/** The Weather screen destination */
object WeatherDestination : NavigationDestination {
    /** @{inheritDoc} */
    override val titleId: Int = com.francescsoftware.weathersample.shared.assets.R.string.weather_label

    /** @{inheritDoc} */
    override val iconId: Int = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_arrow_back

    /** @{inheritDoc} */
    override val iconContentDescriptionId: Int =
        com.francescsoftware.weathersample.shared.assets.R.string.content_description_back

    private const val routeRoot = "weather"
    private const val cityNameArg = "city"
    private const val countryArg = "country"
    private const val countryCodeArg = "countryCode"

    /** The route for the Weather screen */
    const val weatherRoute: String = "$routeRoot/{$cityNameArg}/{$countryArg}/{$countryCodeArg}"

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
