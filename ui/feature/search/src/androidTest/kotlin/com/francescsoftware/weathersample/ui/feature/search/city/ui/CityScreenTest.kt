package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.CityState
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.LoadState
import com.francescsoftware.weathersample.ui.feature.search.navigation.CitySearchDestination
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

private val BarcelonaCityModel = CityResultModel(
    id = 1L,
    favoriteId = -1,
    name = "Barcelona",
    country = "Spain",
    countryCode = "ES",
    coordinates = Coordinates(latitude = 41.39f, longitude = 2.17f),
)

private val recentCities = persistentListOf(
    RecentCityModel("Vancouver"),
    RecentCityModel("Barcelona"),
)

internal class CityScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun circular_progress_shows_when_state_is_loading() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.Loading,
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_cities_loading),
            )
            .assertExists()
    }

    @Test
    fun city_list_shows_when_state_is_loaded() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.Loaded,
                    cities = persistentListOf(
                        BarcelonaCityModel,
                    ),
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_cities_result),
            )
            .assertExists()
    }

    @Test
    fun no_results_shows_when_state_is_no_results() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.NoResults,
                    cities = persistentListOf(),
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_cities_no_results),
            )
            .assertExists()
    }

    @Test
    fun error_shows_when_state_is_error() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.Error,
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_cities_error),
            )
            .assertExists()
    }

    @Test
    fun city_chips_show_when_requested() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.Loaded,
                    recentCities = recentCities,
                    showRecentCities = true,
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_city_chips),
            )
            .assertExists()

        recentCities.forEach { recentCity ->
            composeTestRule
                .onNodeWithContentDescription(
                    recentCity.name,
                )
                .assertExists()
        }
    }

    @Test
    fun search_box_updates_on_recent_city_click() {
        composeTestRule.setContent {
            CityScreen(
                state = CityState.initial.copy(
                    loadState = LoadState.Loaded,
                    recentCities = recentCities,
                    showRecentCities = true,
                ),
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
                onNavigateToCityWeather = { },
                onNavigated = { },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                recentCities.first().name
            )
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.content_description_cities_search_box),
            )
            .assertExists()
            .assertTextContains(recentCities.first().name)
    }
}
