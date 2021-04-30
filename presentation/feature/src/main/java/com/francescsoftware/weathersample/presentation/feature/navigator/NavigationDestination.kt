package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class NavigationDestination {
    @get: StringRes
    abstract val titleId: Int

    @get: DrawableRes
    abstract val iconId: Int

    abstract fun getRoute(): String
    abstract fun isRoute(route: String): Boolean
    abstract fun getArguments(): List<NamedNavArgument>

    object CitySearch : NavigationDestination() {
        override val titleId: Int = R.string.app_name
        override val iconId: Int = 0

        private const val route: String = "city_search"

        override fun getRoute(): String = route
        override fun isRoute(route: String): Boolean = route == this.route
        override fun getArguments(): List<NamedNavArgument> = emptyList()

        fun getDestination(): String = route
    }

    object Weather : NavigationDestination() {
        override val titleId: Int = R.string.weather_label
        override val iconId: Int = R.drawable.ic_arrow_back

        private const val route: String = "weather"
        private const val selectedCityArg: String = "selectedCity"

        override fun getRoute(): String = "$route/{${selectedCityArg}}"
        override fun isRoute(route: String): Boolean = route.startsWith(this.route)
        override fun getArguments(): List<NamedNavArgument> =
            listOf(navArgument(selectedCityArg) { type = NavType.StringType })

        fun getDestination(selectedCity: SelectedCity): String {
            val encodedCity = Json.encodeToString(selectedCity)
            return "$route/$encodedCity"
        }

        fun decode(handle: SavedStateHandle): SelectedCity {
            val city = handle.get<String>(selectedCityArg) ?: throw IllegalStateException("No city provided")
            return Json.decodeFromString(city)
        }
    }
}
