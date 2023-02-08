package com.francescsoftware.weathersample.presentation.route

import androidx.lifecycle.SavedStateHandle
object WeatherDestination : NavigationDestination {
    override val titleId: Int = com.francescsoftware.weathersample.shared.assets.R.string.weather_label
    override val iconId: Int = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_arrow_back
    override val iconContentDescriptionId: Int =
        com.francescsoftware.weathersample.shared.assets.R.string.content_description_back

    private const val routeRoot = "weather"
    private const val cityNameArg = "city"
    private const val countryArg = "country"
    private const val countryCodeArg = "countryCode"

    const val weatherRoute: String = "$routeRoot/{$cityNameArg}/{$countryArg}/{$countryCodeArg}"

    fun getRoute(selectedCity: SelectedCity): String =
        "$routeRoot/${selectedCity.name}/${selectedCity.country}/${selectedCity.countryCode}"

    fun getCity(savedStateHandle: SavedStateHandle): SelectedCity = SelectedCity(
        name = savedStateHandle.get<String>(cityNameArg).orEmpty(),
        country = savedStateHandle.get<String>(countryArg).orEmpty(),
        countryCode = savedStateHandle.get<String>(countryCodeArg).orEmpty(),
    )

    override fun isRoute(route: String?): Boolean = route?.startsWith(routeRoot) == true
}
