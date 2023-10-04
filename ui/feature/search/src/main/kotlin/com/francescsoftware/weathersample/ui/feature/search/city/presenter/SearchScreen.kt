package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetRecentCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.InsertRecentCityInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.RecentCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.composable.common.saver.ImmutableListSaver
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Parcelize
object SearchScreen : Screen {
    sealed interface CitiesResult {
        data object Idle : CitiesResult
        data object Loading : CitiesResult
        data object NoResult : CitiesResult
        data object LoadError : CitiesResult

        data class CitiesLoaded(val cities: ImmutableList<CityResultModel>) : CitiesResult
    }

    data class State(
        val citiesResult: CitiesResult,
        val recentCities: ImmutableList<RecentCityModel>,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object QueryFocused : Event
        data class QueryUpdated(val query: TextFieldValue) : Event
        data class ChipClick(val city: RecentCityModel) : Event
        data class DeleteChipClick(val city: RecentCityModel) : Event
        data class FavoriteClick(val city: CityResultModel) : Event
    }
}

private val DebounceDelay = 400L.toDuration(DurationUnit.MILLISECONDS)
private const val MinCityLengthForSearch = 3
private const val NoFavorite = -1
private const val MaxRecentCities = 10

@CircuitInject(SearchScreen::class, ActivityScope::class)
class SearchPresenter @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val insertFavoriteCityInteractor: InsertFavoriteCityInteractor,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val getRecentCitiesInteractor: GetRecentCitiesInteractor,
    private val insertRecentCitiesInteractor: InsertRecentCityInteractor,
    private val deleteRecentCityInteractor: DeleteRecentCityInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : Presenter<SearchScreen.State> {

    @Composable
    override fun present(): SearchScreen.State {
        var query by rememberSaveable {
            mutableStateOf("")
        }
        var cities: SearchScreen.CitiesResult by remember {
            mutableStateOf(SearchScreen.CitiesResult.Idle)
        }
        var recentCities: ImmutableList<RecentCityModel> by rememberSaveable(
            stateSaver = ImmutableListSaver(),
        ) {
            mutableStateOf(persistentListOf())
        }
        var loadRecentCitiesTrigger by rememberSaveable {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = getFavoriteCitiesInteractor) {
            val searchFlow = snapshotFlow { query }
                .debounce(DebounceDelay)
                .distinctUntilChanged()
                .shareIn(scope = scope, started = SharingStarted.Lazily)
            launch {
                searchFlow
                    .map { it.length >= MinCityLengthForSearch }
                    .collectLatest { searching ->
                        cities = if (searching) SearchScreen.CitiesResult.Loading else SearchScreen.CitiesResult.Idle
                    }
            }
            launch {
                combine(
                    searchFlow
                        .filter { it.length >= MinCityLengthForSearch }
                        .map { query -> getCitiesInteractor(prefix = query) },
                    getFavoriteCitiesInteractor(),
                ) { citiesResponse, favorites ->
                    parseCities(citiesResponse, favorites)
                }.collectLatest { result ->
                    cities = result
                }
            }
        }
        if (loadRecentCitiesTrigger) {
            LaunchedEffect(key1 = getRecentCitiesInteractor) {
                getRecentCitiesInteractor(limit = MaxRecentCities)
                    .map { cities ->
                        parseRecentCities(cities)
                    }
                    .collectLatest { cities ->
                        recentCities = cities
                    }
            }
        }
        fun eventSink(event: SearchScreen.Event) {
            when (event) {
                SearchScreen.Event.QueryFocused -> loadRecentCitiesTrigger = true

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
            }
        }

        return SearchScreen.State(
            citiesResult = cities,
            recentCities = recentCities,
            eventSink = ::eventSink,
        )
    }

    private suspend fun parseCities(
        citiesResponse: Either<Cities>,
        favorites: List<FavoriteCity>,
    ) = citiesResponse.fold(
        onSuccess = { result ->
            parseCities(result, favorites)
        },
        onFailure = {
            SearchScreen.CitiesResult.LoadError
        },
    )

    private suspend fun parseCities(
        cityResponse: Cities,
        favorites: List<FavoriteCity>,
    ) = withContext(dispatcherProvider.default) {
        if (cityResponse.cities.isEmpty()) {
            SearchScreen.CitiesResult.NoResult
        } else {
            val cityList = cityResponse.cities.map { city ->
                val favoriteCity = favorites.firstOrNull { favorite ->
                    favorite.name == city.name && favorite.countryCode == city.countryCode
                }
                city.toCityResultModel(favoriteCity?.id ?: NoFavorite)
            }.toPersistentList()
            SearchScreen.CitiesResult.CitiesLoaded(cityList)
        }
    }

    private suspend fun parseRecentCities(cities: List<RecentCity>) = withContext(dispatcherProvider.default) {
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
