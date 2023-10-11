package com.francescsoftware.weathersample.domain.interactor.weather.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherRepository
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class GetTodayWeatherInteractorImpl @Inject constructor(
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
                        visibility = AverageVisibility.fromKm(weatherResponse.current.visibilityKm),
                        clouds = weatherResponse.toTodayClouds(),
                    )
                    Either.Success(weather)
                }
            },
            onFailure = { throwable ->
                Either.Failure(
                    WeatherException(
                        throwable.message ?: "Error fetching today weather",
                        throwable,
                    ),
                )
            },
        )
    }
}
