package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import com.francescsoftware.weathersample.presentation.feature.R

sealed class NavigationDestination {
    @get: StringRes
    abstract val titleId: Int

    @get: DrawableRes
    abstract val iconId: Int

    abstract fun getRoute(): String
    abstract fun isRoute(route: String?): Boolean
    abstract fun getArguments(): List<NamedNavArgument>

    object CitySearch : NavigationDestination() {
        override val titleId: Int = R.string.app_name
        override val iconId: Int = 0

        private const val route: String = "city_search"

        override fun getRoute(): String = route
        override fun isRoute(route: String?): Boolean = route == this.route
        override fun getArguments(): List<NamedNavArgument> = emptyList()
    }

    object Weather : NavigationDestination() {
        override val titleId: Int = R.string.weather_label
        override val iconId: Int = R.drawable.ic_arrow_back

        private const val route: String = "weather"

        override fun getRoute(): String = route
        override fun isRoute(route: String?): Boolean = route?.startsWith(this.route) == true
        override fun getArguments(): List<NamedNavArgument> = emptyList()
    }
}
