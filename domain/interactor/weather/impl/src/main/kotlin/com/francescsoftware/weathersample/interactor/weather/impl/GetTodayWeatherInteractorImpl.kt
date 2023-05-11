package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

internal class GetTodayWeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
) : GetTodayWeatherInteractor {

    override suspend operator fun invoke(location: WeatherLocation): Either<TodayWeather> {
        val response = weatherRepository.getTodayWeather(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { weatherResponse ->
                withContext(dispatcherProvider.default) {
                    val weather = TodayWeather(
                        main = weatherResponse.toTodayMain(),
                        wind = weatherResponse.toTodayWind(),
                        visibility = weatherResponse.current.visibilityKm.roundToInt(),
                        clouds = weatherResponse.toTodayClouds(),
                    )
                    Either.Success(weather)
                }
            },
            onFailure = { throwable ->
                Either.Failure(
                    WeatherException(
                        throwable.message ?: "Error fetching today weather",
                        throwable
                    )
                )
            },
        )
    }
}
