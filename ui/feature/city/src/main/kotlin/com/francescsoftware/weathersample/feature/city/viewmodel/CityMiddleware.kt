package com.francescsoftware.weathersample.feature.city.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.feature.city.model.CityResultModel
import com.francescsoftware.weathersample.interactor.city.api.DeleteFavoriteCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.interactor.city.api.InsertFavoriteCityInteractor
import com.francescsoftware.weathersample.interactor.city.api.model.City
import com.francescsoftware.weathersample.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.type.map
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private val DebounceMillis = 400L.toDuration(DurationUnit.MILLISECONDS)
private const val MinCityLengthForSearch = 3
private const val NoFavorite = -1

internal class CityMiddleware @Inject constructor(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val insertFavoriteCityInteractor: InsertFavoriteCityInteractor,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
    private val dispatcherProvider: DispatcherProvider,
    private val stringLookup: StringLookup,
) : Middleware<CityState, CityAction>() {

    private val searchFlow = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private var job: Job? = null

    override fun process(
        state: CityState,
        action: CityAction,
    ) {
        when (action) {
            CityAction.Start -> onStart()
            is CityAction.QueryUpdated -> onQueryUpdated(action.query)
            is CityAction.OnFavoriteClick -> onFavoriteClick(action.city)
            else -> {}
        }
    }

    private fun onStart() {
        job?.cancel()
        val searchCities = searchFlow
            .distinctUntilChanged()
            .debounce(DebounceMillis)
            .onEach { dispatch(CityAction.Loading) }
            .map { prefix ->
                getCitiesInteractor(prefix = prefix)
            }
        job = combine(
            searchCities,
            getFavoriteCitiesInteractor(),
        ) { cities, favorites ->
            onCitiesLoaded(
                cities.map { response ->
                    response.cities
                },
                favorites,
            )
        }
            .flowOn(dispatcherProvider.default)
            .launchIn(scope)
    }

    private fun onQueryUpdated(
        query: TextFieldValue,
    ) {
        if (query.text.length >= MinCityLengthForSearch) {
            scope.launch {
                searchFlow.emit(query.text)
            }
        }
    }

    private fun onCitiesLoaded(
        cities: Either<List<City>>,
        favorites: List<FavoriteCity>,
    ) {
        cities.fold(
            onSuccess = { list ->
                if (list.isEmpty()) {
                    dispatch(CityAction.NoResults)
                } else {
                    dispatch(
                        CityAction.CitiesLoaded(
                            list.map { city ->
                                val favoriteCity = favorites.firstOrNull() { favorite ->
                                    favorite.name == city.name && favorite.countryCode == city.countryCode
                                }
                                city.toCityResultModel(favoriteCity?.id ?: NoFavorite)
                            }
                        )
                    )
                }
            },
            onFailure = {
                dispatch(CityAction.LoadError)
            }
        )
    }

    private fun onFavoriteClick(city: CityResultModel) {
        scope.launch {
            if (city.isFavorite) {
                deleteFavoriteCityInteractor(city.toFavoriteCity())
            } else {
                insertFavoriteCityInteractor(city.toFavoriteCity().copy(id = 0))
            }
        }
    }

    private fun City.toCityResultModel(favoriteId: Int) = CityResultModel(
        id = id.toLong(),
        favoriteId = favoriteId,
        name = name,
        country = country,
        countryCode = countryCode,
        coordinates = stringLookup.getString(
            R.string.coordinates_lat_lon,
            coordinates.latitude.toFloat(),
            coordinates.longitude.toFloat(),
        ),
    )

    private fun CityResultModel.toFavoriteCity(): FavoriteCity = FavoriteCity(
        id = favoriteId,
        name = name.toString(),
        countryCode = countryCode,
    )
}
