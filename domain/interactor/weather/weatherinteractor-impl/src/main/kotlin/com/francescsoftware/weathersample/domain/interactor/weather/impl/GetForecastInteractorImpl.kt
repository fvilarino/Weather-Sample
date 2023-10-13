package com.francescsoftware.weathersample.domain.interactor.weather.impl

import com.francescsoftware.weathersample.core.dispather.DispatcherProvider
import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.time.api.TimeParsingException
import com.francescsoftware.weathersample.core.time.api.TimeProvider
import com.francescsoftware.weathersample.core.time.api.ZoneIdProvider
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.fold
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherRepository
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastDay as RepoForecastDay

private class ForecastParseException : RuntimeException()

@ContributesBinding(AppScope::class)
class GetForecastInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val timeProvider: TimeProvider,
    private val zoneIdProvider: ZoneIdProvider,
) : GetForecastInteractor {

    override suspend fun invoke(params: GetForecastInteractor.Params): Either<Forecast> {
        val response = weatherRepository.getForecast(params.location.toRepositoryLocation())
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
                    ),
                )
            },
        )
    }

    private suspend fun parseForecast(
        data: ForecastResponse,
    ): Forecast = withContext(dispatcherProvider.default) {
        // aggregate the forecast by days
        val days: Map<ZonedDateTime, RepoForecastDay> = data.forecast.forecastDay
            .associateBy { item ->
                val epochSeconds = item
                    .hour
                    .firstOrNull()?.timeEpoch ?: throw ForecastParseException()
                val instant = Instant.ofEpochSecond(epochSeconds.toLong())
                instant.atZone(zoneIdProvider.zoneId)
                    .truncatedTo(ChronoUnit.DAYS)
            }

        // convert to forecast days, filtering out hourly forecasts in the past
        val now = timeProvider.epoch.epochSecond
        val forecastDays = days
            .mapValues { entry ->
                ForecastDay(
                    date = entry.key,
                    sunrise = entry.value.astro.sunrise,
                    sunset = entry.value.astro.sunset,
                    entries = entry.value.hour
                        .filter { forecastHour -> forecastHour.timeEpoch >= now }
                        .map { hour -> hour.toForecastEntry(zoneIdProvider.zoneId) },
                )
            }
            .filter { mapEntry -> mapEntry.value.entries.isNotEmpty() }
            .map { entry ->
                entry.value.copy(
                    entries = entry.value.entries.sortedBy { it.zonedDateTime },
                )
            }
        Forecast(
            current = data.current.toCurrent(),
            forecastDays.sortedBy { forecastDay -> forecastDay.date },
        )
    }
}
