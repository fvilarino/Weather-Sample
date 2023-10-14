package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val DebounceDelay = 400.milliseconds
private const val MinCityLengthForSearch = 3
private const val NoFavorite = -1

class CitiesLoader @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val dispatcherProvider: DispatcherProvider,
) {

    private val searchQuery = MutableStateFlow("")

    init {
        getFavoriteCitiesInteractor(Unit)
    }

    val cities: Flow<SearchScreen.CitiesResult> = searchQuery
        .debounce(DebounceDelay)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                if (query.length < MinCityLengthForSearch) {
                    emit(SearchScreen.CitiesResult.Idle)
                } else {
                    emit(SearchScreen.CitiesResult.Loading)
                    val cities = getCitiesInteractor(GetCitiesInteractor.Params(query)).valueOrNull()?.cities
                    when {
                        cities == null -> emit(SearchScreen.CitiesResult.Error)
                        cities.isEmpty() -> emit(SearchScreen.CitiesResult.NoResults)
                        else -> emitAll(
                            getFavoriteCitiesInteractor.stream
                                .distinctUntilChanged()
                                .map<List<FavoriteCity>, SearchScreen.CitiesResult> { favoriteCities ->
                                    SearchScreen.CitiesResult.Loaded(parseCities(cities, favoriteCities))
                                },
                        )
                    }
                }
            }
        }

    fun setQuery(query: String) {
        searchQuery.value = query
    }

    private suspend fun parseCities(
        cities: List<City>,
        favorites: List<FavoriteCity>,
    ) = withContext(dispatcherProvider.default) {
        cities.map { city ->
            val favoriteCity = favorites.firstOrNull { favorite ->
                favorite.name == city.name && favorite.countryCode == city.countryCode
            }
            city.toCityResultModel(favoriteCity?.id ?: NoFavorite)
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
}
