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
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastHour
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.math.roundToInt
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastDay as RepoForecastDay

internal class GetForecastInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeFormatter: TimeFormatter,
    private val timerParser: TimeParser,
) : GetForecastInteractor {

    override suspend operator fun invoke(location: WeatherLocation): Either<Forecast> {
        val response = weatherRepository.getForecast(location.toRepositoryLocation())
        return response.fold(
            onSuccess = { data ->
                val forecast = data.forecast.forecastDay
                if (forecast.isNotEmpty()) {
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
        forecast: List<RepoForecastDay>,
    ): Forecast = withContext(dispatcherProvider.default) {
        // aggregate the forecast by days
        val days: Map<Date, RepoForecastDay> = forecast
            .associateBy { item ->
                val time = item.hour.firstOrNull()?.time ?: throw ForecastParseException()
                timeFormatter.setToMidnight(
                    timerParser.parseDate(Iso8601DateTime(time))
                )
            }

        // convert to forecast days
        val forecastDays: List<ForecastDay> = days
            .mapValues { entry ->
                ForecastDay(
                    date = entry.key,
                    sunrise = entry.value.astro.sunrise,
                    sunset = entry.value.astro.sunset,
                    entries = entry.value.hour.map { hour -> hour.toForecastEntry(timerParser) }
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

    private fun ForecastHour.toForecastEntry(
        timerParser: TimeParser,
    ) = ForecastEntry(
        date = this.time.let { date -> timerParser.parseDate(Iso8601DateTime(date)) },
        description = condition.text,
        iconCode = condition.code,
        temperature = tempCelsius,
        feelsLikeTemperature = feelsLikeCelsius,
        precipitation = precipitationMm.roundToInt(),
        windSpeed = windKph,
        uvIndex = uvIndex.roundToInt(),
        humidityPercent = humidity,
        visibility = visibilityKm.roundToInt(),
    )
}

private class ForecastParseException : RuntimeException()
