package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
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
        getFavoriteCitiesInteractor(GetFavoriteCitiesInteractor.Params)
    }

    val cities: Flow<SearchScreen.CitiesResult> = searchQuery
        .debounce(DebounceDelay)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.length < MinCityLengthForSearch) {
                flowOf<SearchScreen.CitiesResult>(SearchScreen.CitiesResult.Idle)
            } else {
                combine(
                    flow { emit(getCitiesInteractor(GetCitiesInteractor.Params(query))) },
                    getFavoriteCitiesInteractor.stream,
                ) { cities, favorites ->
                    cities.fold(
                        onSuccess = {
                            SearchScreen.CitiesResult.Loaded(parseCities(it.cities, favorites))
                        },
                        onFailure = {
                            SearchScreen.CitiesResult.Error
                        },
                    )
                }.onStart {
                    emit(SearchScreen.CitiesResult.Loading)
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
        val cityList = cities.map { city ->
            val favoriteCity = favorites.firstOrNull { favorite ->
                favorite.name == city.name && favorite.countryCode == city.countryCode
            }
            city.toCityResultModel(favoriteCity?.id ?: NoFavorite)
        }.toPersistentList()
        cityList
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
