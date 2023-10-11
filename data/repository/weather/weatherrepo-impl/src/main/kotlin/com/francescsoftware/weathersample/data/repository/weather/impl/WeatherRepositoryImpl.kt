package com.francescsoftware.weathersample.data.repository.weather.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.core.network.safeApiCall
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherException
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherRepository
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val dispatcherProvider: DispatcherProvider,
) : WeatherRepository {

    override suspend fun getTodayWeather(
        location: WeatherLocation,
    ): Either<TodayWeatherResponse> {
        val response = safeApiCall {
            weatherService.getTodayWeather(
                query = location.formattedQuery,
            )
        }
        return response.fold(
            onSuccess = { model ->
                if (model.isValid) {
                    withContext(dispatcherProvider.default) {
                        Either.Success(model.toWeatherResponse())
                    }
                } else {
                    Either.Failure(
                        WeatherException(
                            message = "Failed to load today weather",
                        ),
                    )
                }
            },
            onFailure = { throwable ->
                Either.Failure(
                    WeatherException(
                        message = throwable.message ?: "Failed to load today weather",
                        cause = throwable,
                    ),
                )
            },
        )
    }

    override suspend fun getForecast(
        location: WeatherLocation,
        days: Int,
    ): Either<ForecastResponse> {
        val response = safeApiCall {
            weatherService.getForecast(
                query = location.formattedQuery,
                days = days,
            )
        }
        return response.fold(
            onSuccess = { model ->
                if (model.isValid) {
                    withContext(dispatcherProvider.default) {
                        Either.Success(model.toForecastResponse())
                    }
                } else {
                    Either.Failure(
                        WeatherException(
                            message = "Failed to load weather forecast",
                        ),
                    )
                }
            },
            onFailure = { throwable ->
                Either.Failure(
                    WeatherException(
                        message = throwable.message ?: "Failed to load weather forecast",
                        cause = throwable,
                    ),
                )
            },
        )
    }

    private val WeatherLocation.formattedQuery: String
        get() = when (this) {
            is WeatherLocation.City -> "$name,$countryCode"
            is WeatherLocation.Coordinates -> "$latitude,$longitude"
        }
}
