package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.location.api.LocationProvider
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesPagingInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCityIdsInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.SelectedCity
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val MinLengthForSearch = 3
private const val CitiesPageSize = 8
private const val InitialPagesLoad = 3
private val QueryDebounceTime = 400.milliseconds
private val CitiesPagingConfig = PagingConfig(
    pageSize = CitiesPageSize,
    initialLoadSize = CitiesPageSize * InitialPagesLoad,
)

class SearchPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val getCitiesInteractor: GetCitiesPagingInteractor,
    private val getFavoriteCityIdsInteractor: GetFavoriteCityIdsInteractor,
    private val recentCitiesLoader: RecentCitiesLoader,
    private val insertFavoriteCityInteractor: InsertFavoriteCityInteractor,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
    private val locationProvider: LocationProvider,
) : Presenter<SearchScreen.State> {

    @CircuitInject(SearchScreen::class, ActivityScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            navigator: Navigator,
        ): SearchPresenter
    }

    @Composable
    @Suppress("CyclomaticComplexMethod")
    override fun present(): SearchScreen.State {
        val scope = rememberCoroutineScope()
        var firstLoad by remember { mutableStateOf(true) }
        var query by rememberSaveable { mutableStateOf("") }
        val cities = remember(getCitiesInteractor.stream, scope) {
            getCitiesInteractor.stream.cachedIn(scope)
        }.collectAsLazyPagingItems()
        var lastLocation: Coordinates? by rememberRetained { mutableStateOf(null) }
        val showResults by remember {
            derivedStateOf { lastLocation != null || query.length >= MinLengthForSearch }
        }
        val favorites by getFavoriteCityIdsInteractor.stream
            .collectAsRetainedState(initial = emptySet())
        val recentCities by recentCitiesLoader.recentCities
            .collectAsRetainedState(initial = persistentListOf())
        LaunchedEffect(key1 = Unit) {
            getFavoriteCityIdsInteractor(Unit)
        }
        LaunchedEffect(key1 = query) {
            val canSearch = query.length >= MinLengthForSearch
            if (canSearch) {
                lastLocation = null
            }
            if (!(firstLoad && canSearch)) {
                delay(QueryDebounceTime)
            }
            firstLoad = false
            if (canSearch) {
                getCitiesInteractor(
                    GetCitiesPagingInteractor.Parameters.PrefixParameters(
                        pagingConfig = CitiesPagingConfig,
                        prefix = query,
                    ),
                )
            }
        }
        LaunchedEffect(key1 = lastLocation) {
            lastLocation?.let { coordinates ->
                getCitiesInteractor(
                    GetCitiesPagingInteractor.Parameters.CoordinateParameters(
                        pagingConfig = CitiesPagingConfig,
                        coordinates = coordinates,
                    ),
                )
            }
        }
        fun eventSink(event: SearchScreen.Event) {
            when (event) {
                SearchScreen.Event.LocationClick -> scope.launch {
                    lastLocation = locationProvider.getCurrentLocation()
                }

                SearchScreen.Event.RetryClick -> cities.retry()
                is SearchScreen.Event.QueryUpdated -> query = event.query.text

                is SearchScreen.Event.ChipClick -> scope.launch {
                    insertRecentCitiesInteractor(
                        InsertRecentCityInteractor.Params(
                            RecentCity(name = event.city.name),
                        ),
                    )
                }

                is SearchScreen.Event.DeleteChipClick -> scope.launch {
                    deleteRecentCityInteractor(
                        DeleteRecentCityInteractor.Params(
                            city = RecentCity(name = event.city.name),
                        ),
                    )
                }

                is SearchScreen.Event.FavoriteClick -> scope.launch {
                    val cityId = event.city.id
                    val isFavorite = favorites.contains(cityId)
                    if (isFavorite) {
                        deleteFavoriteCityInteractor(
                            DeleteFavoriteCityInteractor.Params(cityId),
                        )
                    } else {
                        insertFavoriteCityInteractor(
                            InsertFavoriteCityInteractor.Param(event.city.toFavoriteCity()),
                        )
                    }
                }

                is SearchScreen.Event.CityClick -> scope.launch {
                    insertRecentCitiesInteractor(
                        InsertRecentCityInteractor.Params(RecentCity(name = event.city.name)),
                    )
                    navigator.goTo(WeatherScreen(event.city.toSelectedCity()))
                }
            }
        }

        return SearchScreen.State(
            showResults = showResults,
            cities = cities,
            favoriteCities = favorites.toImmutableSet(),
            recentCities = recentCities,
            eventSink = ::eventSink,
        )
    }

    private fun City.toFavoriteCity(): FavoriteCity = FavoriteCity(
        cityId = id,
        name = name,
        countryCode = countryCode,
    )

    private fun City.toSelectedCity(): SelectedCity = SelectedCity(
        name = name,
        countryCode = countryCode,
    )
}
