package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeatherItem
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Clouds
import com.francescsoftware.weathersample.weatherrepository.api.model.Main
import com.francescsoftware.weathersample.weatherrepository.api.model.WeatherItem
import com.francescsoftware.weathersample.weatherrepository.api.model.Wind
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import javax.inject.Inject

internal class GetTodayWeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetTodayWeatherInteractor {

    override suspend fun execute(location: WeatherLocation): Result<TodayWeather> {
        val response = weatherRepository.getTodayWeather(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { weatherResponse ->
                if (weatherResponse.isValid) {
                    val weather = TodayWeather(
                        weather = weatherResponse.toWeather(),
                        main = weatherResponse.main.toMain(),
                        wind = weatherResponse.wind.toWind(),
                        visibility = weatherResponse.visibility ?: 0,
                        clouds = weatherResponse.clouds.toClouds()
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

    private val TodayWeatherResponse.isValid: Boolean
        get() = weather?.isValid == true &&
            main?.isValid == true &&
            wind?.isValid == true &&
            clouds?.isValid == true &&
            visibility != null

    private val List<WeatherItem>.isValid: Boolean
        get() = isNotEmpty() && first().isValid

    private val WeatherItem.isValid: Boolean
        get() = icon != null && description != null && main != null

    private val Main.isValid: Boolean
        get() = temp != null &&
            tempMin != null &&
            tempMax != null &&
            feelsLike != null &&
            humidity != null &&
            pressure != null

    private val Wind.isValid: Boolean
        get() = deg != null && speed != null

    private val Clouds.isValid: Boolean
        get() = all != null

    private fun TodayWeatherResponse?.toWeather(): TodayWeatherItem {
        val item = this?.weather?.firstOrNull()
        return TodayWeatherItem(
            icon = item?.icon.orEmpty(),
            description = item?.description.orEmpty(),
            main = item?.main.orEmpty(),
        )
    }

    private fun Main?.toMain(): TodayMain {
        check(this != null) { "Weather main is null" }
        return TodayMain(
            temp = temp ?: 0.0,
            tempMin = tempMin ?: 0.0,
            tempMax = tempMax ?: 0.0,
            feelsLike = feelsLike ?: 0.0,
            humidity = humidity ?: 0,
            pressure = pressure ?: 0,
        )
    }

    private fun Wind?.toWind(): TodayWind {
        check(this != null) { "Weather wind is null" }
        return TodayWind(
            deg = deg ?: 0,
            speed = speed ?: 0.0,
            gust = gust ?: 0.0,
        )
    }

    private fun Clouds?.toClouds(): TodayClouds {
        check(this != null) { "Weather clouds is null" }
        return TodayClouds(
            all = all ?: 0,
        )
    }
}
