package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.utils.time.Seconds
import com.francescsoftware.weathersample.utils.time.toDate
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Astro
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastDayItem
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.HourItem
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.math.roundToInt

internal class GetForecastInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeFormatter: TimeFormatter,
) : GetForecastInteractor {

    override suspend fun execute(location: WeatherLocation): Result<Forecast> {
        val response = weatherRepository.getForecast(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { data ->
                val forecast = data.forecast?.forecastDay
                if (forecast?.isNotEmpty() == true) {
                    Result.Success(parseForecast(forecast))
                } else {
                    Result.Failure(WeatherException("Invalid forecast data received"))
                }
            },
            onFailure = { throwable ->
                Result.Failure(
                    WeatherException(
                        throwable.message ?: "Error fetching forecast",
                        throwable,
                    )
                )
            }
        )
    }

    private suspend fun parseForecast(
        forecast: List<ForecastDayItem>,
    ): Forecast = withContext(dispatcherProvider.default) {

        // aggregate the forecast by days
        val days: Map<Date, ForecastDayItem> = forecast
            .filter { item -> item.isValid }
            .associateBy { item ->
                timeFormatter.setToMidnight(
                    Seconds((item.dateEpoch ?: 0).toLong()).toDate()
                )
            }
            .filterValues { item -> item.isValid }

        // convert to forecast days
        val forecastDays: List<ForecastDay> = days
            .mapValues { entry ->
                ForecastDay(
                    date = entry.key,
                    sunrise = entry.value.astro?.sunrise.orEmpty(),
                    sunset = entry.value.astro?.sunset.orEmpty(),
                    entries = entry.value.hour?.map { hour -> hour.toForecastEntry() }.orEmpty()
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

    private fun HourItem.toForecastEntry() = ForecastEntry(
        date = Seconds((timeEpoch ?: 0).toLong()).toDate(),
        description = condition?.text.orEmpty(),
        iconCode = condition?.code ?: 0,
        temperature = tempC ?: 0.0,
        feelsLikeTemperature = feelslikeC ?: 0.0,
        precipitation = precipMm?.roundToInt() ?: 0,
        windSpeed = windKph ?: 0.0,
        uvIndex = uv?.roundToInt() ?: 0,
        humidityPercent = humidity ?: 0,
        visibility = visKm?.roundToInt() ?: 0,
    )

    private val ForecastDayItem.isValid: Boolean
        get() = astro.isValid && hour.isValid

    private val Astro?.isValid: Boolean
        get() = this != null && sunrise?.isNotEmpty() == true && sunset?.isNotEmpty() == true

    private val List<HourItem>?.isValid: Boolean
        get() = this?.isNotEmpty() == true && all { item -> item.isValid }

    private val HourItem.isValid: Boolean
        get() = timeEpoch != null &&
            tempC != null &&
            feelslikeC != null &&
            windKph != null &&
            humidity != null &&
            visKm != null &&
            condition.isValid

    private val Condition?.isValid: Boolean
        get() = this != null && code != null && icon != null && text != null
}
