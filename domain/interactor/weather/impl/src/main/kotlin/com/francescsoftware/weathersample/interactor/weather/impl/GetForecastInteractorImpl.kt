package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.dispather.DispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.time.api.TimeParser
import com.francescsoftware.weathersample.time.api.TimeParsingException
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
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
                if (data.forecast.forecastDay.isNotEmpty()) {
                    try {
                        Either.Success(parseForecast(data))
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
        data: ForecastResponse,
    ): Forecast = withContext(dispatcherProvider.default) {
        // aggregate the forecast by days
        val days: Map<Date, RepoForecastDay> = data.forecast.forecastDay
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
            current = data.current.toCurrent(),
            forecastDays.sortedBy { forecastDay -> forecastDay.date }
        )
    }
}

private class ForecastParseException : RuntimeException()
