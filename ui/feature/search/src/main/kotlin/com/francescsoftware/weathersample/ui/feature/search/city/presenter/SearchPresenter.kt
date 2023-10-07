package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
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
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private val DebounceDelay = 400L.toDuration(DurationUnit.MILLISECONDS)
private const val MinCityLengthForSearch = 3
private const val NoFavorite = -1
private const val MaxRecentCities = 10

private sealed interface CitiesSearch {
    data object Loading : CitiesSearch
    data object QueryTooShort : CitiesSearch
    data object NoResult : CitiesSearch
    data object LoadError : CitiesSearch
    data class Loaded(val cities: List<City>) : CitiesSearch
}


class SearchPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val getCitiesInteractor: GetCitiesInteractor,
    private val insertFavoriteCityInteractor: InsertFavoriteCityInteractor,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : Presenter<SearchScreen.State> {

    @CircuitInject(SearchScreen::class, ActivityScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            navigator: Navigator,
        ): SearchPresenter
    }

    @Composable
    override fun present(): SearchScreen.State {
        val scope = rememberCoroutineScope()
        var query by rememberSaveable {
            mutableStateOf("")
        }
        val citiesFlow = rememberRetained(
            inputs = arrayOf(query, getCitiesInteractor)
        ) {
            snapshotFlow { query }
                .debounce(DebounceDelay)
                .distinctUntilChanged()
                .flatMapLatest<String, CitiesSearch> { searchQuery ->
                    if (searchQuery.length < MinCityLengthForSearch) {
                        flowOf(CitiesSearch.QueryTooShort)
                    } else {
                        flow {
                            emit(CitiesSearch.Loading)
                            getCitiesInteractor(prefix = searchQuery).fold(
                                onSuccess = { result ->
                                    if (result.cities.isEmpty()) {
                                        emit(CitiesSearch.NoResult)
                                    } else {
                                        emit(CitiesSearch.Loaded(result.cities))
                                    }
                                },
                                onFailure = {
                                    emit(CitiesSearch.LoadError)
                                },
                            )
                        }
                    }
                }
                .shareIn(scope = scope, started = SharingStarted.Lazily)
        }
        val citiesResultFlow = rememberRetained(
            inputs = arrayOf(citiesFlow, getFavoriteCitiesInteractor),
        ) {
            combine(
                citiesFlow,
                getFavoriteCitiesInteractor(),
            ) { citiesResponse, favorites ->
                parseCities(citiesResponse, favorites)
            }
        }
        val cities by citiesResultFlow.collectAsRetainedState(initial = SearchScreen.CitiesResult.Idle)
        val recentCities by getRecentCitiesInteractor(limit = MaxRecentCities)
            .map { recentCities -> parseRecentCities(recentCities) }
            .collectAsRetainedState(initial = persistentListOf())

        fun eventSink(event: SearchScreen.Event) {
            when (event) {
                is SearchScreen.Event.QueryUpdated -> query = event.query.text

                is SearchScreen.Event.ChipClick -> scope.launch {
                    insertRecentCitiesInteractor(RecentCity(name = event.city.name))
                }

                is SearchScreen.Event.DeleteChipClick -> scope.launch {
                    deleteRecentCityInteractor(city = RecentCity(name = event.city.name))
                }

                is SearchScreen.Event.FavoriteClick -> scope.launch {
                    if (event.city.isFavorite) {
                        deleteFavoriteCityInteractor(event.city.toFavoriteCity())
                    } else {
                        insertFavoriteCityInteractor(event.city.toFavoriteCity().copy(id = 0))
                    }
                }

                is SearchScreen.Event.CityClick -> {
                    scope.launch {
                        insertRecentCitiesInteractor(RecentCity(name = event.city.name))
                    }
                    navigator.goTo(WeatherScreen(event.city))
                }
            }
        }

        return SearchScreen.State(
            citiesResult = cities,
            recentCities = recentCities,
            eventSink = ::eventSink,
        )
    }

    private suspend fun parseCities(
        citiesResponse: CitiesSearch,
        favorites: List<FavoriteCity>,
    ) = when (citiesResponse) {
        CitiesSearch.QueryTooShort -> SearchScreen.CitiesResult.Idle
        CitiesSearch.Loading -> SearchScreen.CitiesResult.Loading
        CitiesSearch.LoadError -> SearchScreen.CitiesResult.LoadError
        CitiesSearch.NoResult -> SearchScreen.CitiesResult.NoResult
        is CitiesSearch.Loaded -> SearchScreen.CitiesResult.CitiesLoaded(
            parseCities(
                citiesResponse.cities,
                favorites,
            ),
        )
    }

    private suspend fun parseCities(
        cities: List<City>,
        favorites: List<FavoriteCity>,
    ) = withContext(dispatcherProvider.default) {
        val cityList = cities.map { city ->
            val favoriteCity = favorites.firstOrNull { favorite ->
                favorite.name == city.name && favorite.countryCode == city.countryCode
            }
            city.toCityResultModel(favoriteCity?.id ?: NoFavorite)
        }.toPersistentList()
        cityList
    }

    private suspend fun parseRecentCities(cities: List<RecentCity>) =
        withContext(dispatcherProvider.default) {
            cities.map { city ->
                RecentCityModel(name = city.name)
            }.toPersistentList()
        }

    private fun City.toCityResultModel(favoriteId: Int) = CityResultModel(
        id = id.toLong(),
        favoriteId = favoriteId,
        name = name,
        country = country,
        countryCode = countryCode,
        coordinates = Coordinates(
            latitude = coordinates.latitude.toFloat(),
            longitude = coordinates.longitude.toFloat(),
        ),
    )

    private fun CityResultModel.toFavoriteCity(): FavoriteCity = FavoriteCity(
        id = favoriteId,
        name = name,
        countryCode = countryCode,
    )
}
