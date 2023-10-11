package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
internal class CityScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun circular_progress_shows_when_state_is_loading() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Loading,
                        recentCities = persistentListOf(),
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_cities_loading),
            )
            .assertExists()
    }

    @Test
    fun city_list_shows_when_state_is_loaded() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Loaded(
                            persistentListOf(BarcelonaCityModel),
                        ),
                        recentCities = persistentListOf(),
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_cities_result),
            )
            .assertExists()
    }

    @Test
    fun no_results_shows_when_state_is_no_results() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.NoResults,
                        recentCities = persistentListOf(),
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_cities_no_results),
            )
            .assertExists()
    }

    @Test
    fun error_shows_when_state_is_error() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Error,
                        recentCities = persistentListOf(),
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_cities_error),
            )
            .assertExists()
    }

    @Test
    fun city_chips_show_when_available() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Loaded(
                            persistentListOf(BarcelonaCityModel),
                        ),
                        recentCities = recentCities,
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_city_chips),
            )
            .assertExists()

        recentCities.forEach { recentCity ->
            composeTestRule
                .onNodeWithTag(
                    recentCity.name,
                )
                .assertExists()
        }
    }

    @Test
    fun search_box_updates_on_recent_city_click() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                CityScreen(
                    state = SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Loaded(
                            persistentListOf(BarcelonaCityModel),
                        ),
                        recentCities = recentCities,
                        eventSink = {},
                    ),
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                recentCities.first().name,
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                composeTestRule.activity.resources.getString(R.string.test_tag_cities_search_box),
            )
            .assertExists()
            .assertTextContains(recentCities.first().name)
    }

    @Composable
    private fun WithPhoneWindowSizeClass(
        content: @Composable () -> Unit,
    ) {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(PhoneDpSize),
        ) {
            content()
        }
    }
}
