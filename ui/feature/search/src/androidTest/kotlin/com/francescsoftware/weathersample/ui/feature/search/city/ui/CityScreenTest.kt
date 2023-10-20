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
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import java.io.IOException

private val BarcelonaCity = City(
    id = 1L,
    name = "Barcelona",
    region = "Catalunya",
    regionCode = "CA",
    country = "Spain",
    countryCode = "ES",
    coordinates = Coordinates(latitude = 41.39, longitude = 2.17),
)

private val recentCities = persistentListOf(
    RecentCityModel("Vancouver"),
    RecentCityModel("Barcelona"),
)

internal class CityScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_shows_when_fetching_data() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                val cities = listOf(
                    BarcelonaCity,
                )
                val pagingData: Flow<PagingData<City>> = flow {
                    PagingData.from(
                        data = cities,
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Loading,
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        )
                    )
                }
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = persistentListOf(),
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
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
    fun no_results_shows_when_no_cities_available() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                val cities = emptyList<City>()
                val pagingData = MutableStateFlow(PagingData.from(cities))
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = persistentListOf(),
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
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
    fun city_list_shows_when_state_is_loaded() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                val cities = listOf(
                    BarcelonaCity,
                )
                val pagingData = MutableStateFlow(PagingData.from(cities))
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = persistentListOf(),
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }

        composeTestRule
            .onNodeWithTag(
                "${composeTestRule.activity.resources.getString(R.string.test_tag_cities_result)}_${BarcelonaCity.id}",
            )
            .assertExists()
    }

    @Test
    fun error_shows_when_state_is_error() {
        composeTestRule.setContent {
            WithPhoneWindowSizeClass {
                val cities = listOf(
                    BarcelonaCity,
                )
                val pagingData: Flow<PagingData<City>> = MutableStateFlow(
                    PagingData.from(
                        data = cities,
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(IOException()),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        )
                    )
                )
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = persistentListOf(),
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
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
                val cities = listOf(
                    BarcelonaCity,
                )
                val pagingData = MutableStateFlow(PagingData.from(cities))
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = recentCities,
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
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
                val cities = listOf(
                    BarcelonaCity,
                )
                val pagingData = MutableStateFlow(PagingData.from(cities))
                CityScreen(
                    cities = pagingData.collectAsLazyPagingItems(),
                    favoriteCities = persistentSetOf(),
                    recentCities = recentCities,
                    showResults = true,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onRetryClick = { },
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

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
