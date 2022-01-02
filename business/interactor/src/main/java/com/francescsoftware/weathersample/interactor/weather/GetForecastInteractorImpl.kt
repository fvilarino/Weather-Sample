package com.francescsoftware.weathersample.interactor.weather

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.repository.weather.WeatherRepository
import com.francescsoftware.weathersample.repository.weather.model.forecast.City
import com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.utils.time.Seconds
import com.francescsoftware.weathersample.utils.time.toDate
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetForecastInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeFormatter: TimeFormatter,
) : GetForecastInteractor {

    override suspend fun execute(location: WeatherLocation): Result<Forecast> {
        val response = weatherRepository.getForecast(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { data ->
                val forecast = data.forecast
                val city = data.city
                if (forecast?.isNotEmpty() == true && city?.isValid == true) {
                    Result.Success(parseForecast(forecast, city))
                } else {
                    Result.Failure(WeatherException("Invalid forecast data received"))
                }
            },
            onFailure = { throwable ->
                Result.Failure(
                    WeatherException(
                        throwable.message ?: "Error fetching forecast",
                        throwable
                    )
                )
            }
        )
    }

    private suspend fun parseForecast(
        forecast: List<ForecastItem>,
        city: City
    ): Forecast = withContext(dispatcherProvider.default) {
        val sunrise = Seconds((city.sunrise ?: 0).toLong()).toDate()
        val sunset = Seconds((city.sunset ?: 0).toLong()).toDate()
        // aggregate the forecast by days
        val days: Map<Date, List<ForecastItem>> = forecast
            .filter { item -> item.isValid }
            .groupBy { item ->
                timeFormatter.setToMidnight(
                    Seconds((item.epoch ?: 0).toLong()).toDate()
                )
            }

        // convert to forecast days
        val forecastDays: List<ForecastDay> = days
            .filterValues { items -> items.isNotEmpty() }
            .mapValues { entry ->
                ForecastDay(
                    date = entry.key,
                    sunrise = sunrise,
                    sunset = sunset,
                    entries = entry.value.map { value -> value.toForecastEntry() }
                )
            }
            .map { entry ->
                entry.value.copy(
                    entries = entry.value.entries.sortedBy { it.date }
                )
            }
        Forecast(
            forecastDays.sortedBy { forecastDay -> forecastDay.date }
        )
    }

    private fun ForecastItem.toForecastEntry() = ForecastEntry(
        date = Seconds((this.epoch ?: 0).toLong()).toDate(),
        description = weather?.firstOrNull()?.description.orEmpty(),
        icon = weather?.firstOrNull()?.icon.orEmpty(),
        minTemperature = main?.tempMin ?: 0.0,
        maxTemperature = main?.tempMax ?: 0.0,
        feelsLikeTemperature = main?.feelsLike ?: 0.0,
        windSpeed = wind?.speed ?: 0.0,
        humidityPercent = main?.humidity ?: 0,
        visibility = visibility ?: 0,
    )

    private val City.isValid: Boolean
        get() = sunrise != null && sunset != null

    private val ForecastItem.isValid: Boolean
        get() = epoch != null &&
            main != null &&
            main?.tempMin != null &&
            main?.tempMax != null &&
            main?.feelsLike != null &&
            weather?.firstOrNull()?.icon != null
}
