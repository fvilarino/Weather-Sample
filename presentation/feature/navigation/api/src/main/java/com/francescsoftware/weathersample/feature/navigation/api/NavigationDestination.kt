package com.francescsoftware.weathersample.feature.navigation.api

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle

sealed interface NavigationDestination {
    @get: StringRes
    val titleId: Int

    @get: DrawableRes
    val iconId: Int

    @get: StringRes
    val iconContentDescriptionId: Int

    fun isRoute(route: String?): Boolean

    object CitySearch : NavigationDestination {
        override val titleId: Int = R.string.city_label
        override val iconId: Int = 0
        override val iconContentDescriptionId: Int = 0

        const val cityRoute: String = "city_search"

        fun getRoute(): String = cityRoute
        override fun isRoute(route: String?): Boolean = route == cityRoute
    }

    object Weather : NavigationDestination {
        override val titleId: Int = R.string.weather_label
        override val iconId: Int = R.drawable.ic_arrow_back
        override val iconContentDescriptionId: Int = R.string.content_description_back

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
}
