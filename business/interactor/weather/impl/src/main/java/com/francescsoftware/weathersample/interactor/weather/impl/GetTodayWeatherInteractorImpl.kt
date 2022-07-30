package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.Current
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import javax.inject.Inject
import kotlin.math.roundToInt

internal class GetTodayWeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetTodayWeatherInteractor {

    override suspend fun execute(location: WeatherLocation): Result<TodayWeather> {
        val response = weatherRepository.getTodayWeather(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { weatherResponse ->
                if (weatherResponse.isValid) {
                    val weather = TodayWeather(
                        main = weatherResponse.toTodayMain(),
                        wind = weatherResponse.toTodayWind(),
                        visibility = weatherResponse.current?.visKm?.roundToInt() ?: 0,
                        clouds = weatherResponse.toTodayClouds(),
                    )
                    Result.Success(weather)
                } else {
                    Result.Failure(WeatherException("Invalid data received"))
                }
            },
            onFailure = { throwable ->
                Result.Failure(
                    WeatherException(
                        throwable.message ?: "Error fetching today weather",
                        throwable
                    )
                )
            },
        )
    }

    private fun TodayWeatherResponse.toTodayMain(): TodayMain =
        TodayMain(
            code = current?.condition?.code ?: 0,
            description = current?.condition?.text.orEmpty(),
            temp = current?.tempC ?: 0.0,
            feelsLike = current?.feelslikeC ?: 0.0,
            humidity = current?.humidity ?: 0,
            pressure = current?.pressureMb?.roundToInt() ?: 0,
            precipitation = current?.precipMm?.roundToInt() ?: 0,
            uvIndex = current?.uv?.roundToInt() ?: 0,
        )

    private fun TodayWeatherResponse.toTodayWind(): TodayWind =
        TodayWind(
            direction = current?.windDir.orEmpty(),
            speed = current?.windKph ?: 0.0,
            gust = current?.gustKph ?: 0.0,
        )

    private fun TodayWeatherResponse.toTodayClouds(): TodayClouds =
        TodayClouds(
            all = current?.cloud ?: 0,
        )

    private val TodayWeatherResponse.isValid: Boolean
        get() = current.isValid

    private val Current?.isValid: Boolean
        get() = this != null &&
            condition.isValid &&
            tempC != null &&
            tempF != null &&
            feelslikeC != null &&
            feelslikeF != null &&
            humidity != null &&
            pressureMb != null &&
            windDir != null &&
            windKph != null &&
            gustKph != null &&
            visKm != null &&
            cloud != null

    private val Condition?.isValid: Boolean
        get() = this != null && code != null && icon != null && text != null
}
