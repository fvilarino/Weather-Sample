package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.isFailure
import com.francescsoftware.weathersample.core.type.either.map
import com.francescsoftware.weathersample.core.type.either.valueOrThrow
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.ui.feature.favorites.ui.City
import com.francescsoftware.weathersample.ui.feature.favorites.ui.FavoriteCardState
import com.francescsoftware.weathersample.ui.feature.favorites.ui.FavoritePagerState
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import com.francescsoftware.weathersample.ui.shared.mvi.Middleware
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private data class ForecastInfo(
    val city: FavoriteCity,
    val forecast: Forecast,
)

internal class FavoriteWeatherMiddleware @Inject constructor(
    private val getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : Middleware<FavoriteState, FavoriteAction>() {
    private var favoritesJob: Job? = null

    override fun process(state: FavoriteState, action: FavoriteAction) {
        when (action) {
            FavoriteAction.Load -> load()
            else -> {}
        }
    }

    private fun load() {
        favoritesJob?.cancel()
        favoritesJob = getFavoriteCitiesInteractor()
            .map { cities ->
                fetchForecast(cities)
            }
            .onEach { forecasts ->
                parseForecast(forecasts)
            }
            .launchIn(scope)
    }

    private suspend fun fetchForecast(
        cities: List<FavoriteCity>
    ) = if (cities.isEmpty()) {
        emptyList()
    } else {
        cities.map { city ->
            getForecastInteractor(
                location = WeatherLocation.City(
                    name = city.name,
                    countryCode = city.countryCode,
                )
            ).map { forecast ->
                ForecastInfo(
                    city = city,
                    forecast = forecast,
                )
            }
        }
    }

    private fun parseForecast(forecasts: List<Either<ForecastInfo>>) {
        when {
            forecasts.isEmpty() -> dispatch(FavoriteAction.NoFavorites)
            forecasts.any { forecast -> forecast.isFailure } -> dispatch(FavoriteAction.LoadError)
            else -> {
                val data = forecasts.map { result ->
                    result.valueOrThrow()
                }
                val state = FavoritePagerState(
                    pages = data.map { forecastInfo ->
                        val current = forecastInfo.forecast.current
                        FavoriteCardState(
                            city = City(
                                favoriteId = forecastInfo.city.id,
                                name = forecastInfo.city.name,
                                countryCode = forecastInfo.city.countryCode,
                            ),
                            current = current.toWeatherCardState(),
                            forecast = forecastInfo.forecast.toForecastItems(
                                stringLookup,
                                timeFormatter,
                            ).toPersistentList()
                        )
                    }.toPersistentList()
                )
                dispatch(FavoriteAction.Loaded(state))
            }
        }
    }
}
