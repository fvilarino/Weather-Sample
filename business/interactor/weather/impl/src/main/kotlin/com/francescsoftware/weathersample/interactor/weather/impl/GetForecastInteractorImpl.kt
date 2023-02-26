package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.time.api.TimeParser
import com.francescsoftware.weathersample.time.api.TimeParsingException
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.fold
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
    private val timerParser: TimeParser,
) : GetForecastInteractor {

    override suspend fun execute(location: WeatherLocation): Either<Forecast> {
        val response = weatherRepository.getForecast(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { data ->
                val forecast = data.forecast?.forecastDay
                if (forecast?.isNotEmpty() == true) {
                    try {
                        Either.Success(parseForecast(forecast))
                    } catch (ex: ForecastParseException) {
                        Either.Failure(WeatherException(cause = ex))
                    } catch (ex: TimeParsingException) {
                        Either.Failure(WeatherException(cause = ex))
                    }
                } else {
                    Either.Failure(WeatherException("Invalid forecast data received"))
                }
            },
            onFailure = { throwable ->
                Either.Failure(
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
                val time = item.hour?.firstOrNull()?.time ?: throw ForecastParseException()
                timeFormatter.setToMidnight(
                    timerParser.parseDate(Iso8601DateTime(time))
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
                    entries = entry.value.hour?.map { hour -> hour.toForecastEntry(timerParser) }.orEmpty()
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

    private fun HourItem.toForecastEntry(
        timerParser: TimeParser,
    ) = ForecastEntry(
        date = this.time?.let { date -> timerParser.parseDate(Iso8601DateTime(date)) } ?: Date(),
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
        get() = time != null &&
            timeEpoch != null &&
            tempC != null &&
            feelslikeC != null &&
            windKph != null &&
            humidity != null &&
            visKm != null &&
            condition.isValid

    private val Condition?.isValid: Boolean
        get() = this != null && code != null && icon != null && text != null
}

private class ForecastParseException : RuntimeException()
