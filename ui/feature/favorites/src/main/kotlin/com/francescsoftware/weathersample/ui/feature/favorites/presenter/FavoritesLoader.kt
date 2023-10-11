package com.francescsoftware.weathersample.ui.feature.favorites.presenter

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.francescsoftware.weathersample.core.time.api.isToday
import com.francescsoftware.weathersample.core.time.api.isTomorrow
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.isFailure
import com.francescsoftware.weathersample.core.type.either.map
import com.francescsoftware.weathersample.core.type.either.valueOrThrow
import com.francescsoftware.weathersample.domain.interactor.city.api.GetFavoriteCitiesInteractor
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastDate
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.ui.shared.weathericon.drawableId
import com.francescsoftware.weathersample.ui.shared.weathericon.weatherIconFromCode
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import java.util.Locale

private data class ForecastInfo(
    val city: FavoriteCity,
    val forecast: Forecast,
)

internal class FavoritesLoader(
    getFavoriteCitiesInteractor: GetFavoriteCitiesInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
) {
    val favoritesState: Flow<FavoritesScreen.FavoritesState> = getFavoriteCitiesInteractor()
        .map { cities ->
            parseForecast(fetchForecast(cities))
        }

    private suspend fun fetchForecast(
        cities: List<FavoriteCity>,
    ) = if (cities.isEmpty()) {
        emptyList()
    } else {
        cities.map { city ->
            getForecastInteractor(
                location = WeatherLocation.City(
                    name = city.name,
                    countryCode = city.countryCode,
                ),
            ).map { forecast ->
                ForecastInfo(
                    city = city,
                    forecast = forecast,
                )
            }
        }
    }

    private fun parseForecast(forecasts: List<Either<ForecastInfo>>): FavoritesScreen.FavoritesState = when {
        forecasts.isEmpty() -> FavoritesScreen.FavoritesState.NoFavorites
        forecasts.any { forecast -> forecast.isFailure } -> FavoritesScreen.FavoritesState.Error
        else -> {
            val data = forecasts.map { result ->
                result.valueOrThrow()
            }
            val state = FavoritesScreen.FavoritePagerState(
                pages = data.map { forecastInfo ->
                    val current = forecastInfo.forecast.current
                    FavoritesScreen.FavoriteCardState(
                        city = FavoritesScreen.City(
                            favoriteId = forecastInfo.city.id,
                            name = forecastInfo.city.name,
                            countryCode = forecastInfo.city.countryCode,
                        ),
                        current = current.toWeatherCardState(),
                        forecast = forecastInfo.forecast.toForecastItems(
                            timeFormatter,
                        ).toPersistentList(),
                    )
                }.toPersistentList(),
            )
            FavoritesScreen.FavoritesState.Loaded(state)
        }
    }

    private fun Forecast.toForecastItems(
        timeFormatter: TimeFormatter,
    ): List<FavoritesScreen.ForecastDayState> = items.map { forecastDay ->
        FavoritesScreen.ForecastDayState(
            header = forecastDay.toForecastHeaderState(timeFormatter),
            forecast = forecastDay
                .entries.map { entry ->
                    entry.toForecastCardState(timeFormatter)
                }
                .toImmutableList(),
        )
    }

    private fun ForecastDay.toForecastHeaderState(
        timeFormatter: TimeFormatter,
    ): ForecastHeaderState =
        ForecastHeaderState(
            id = "header_$date",
            date = date.toHeaderLabel(timeFormatter),
            sunrise = sunrise,
            sunset = sunset,
        )

    private fun ZonedDateTime.toHeaderLabel(
        timeFormatter: TimeFormatter,
    ): ForecastDate = when {
        isToday -> ForecastDate.Today
        isTomorrow -> ForecastDate.Tomorrow
        else -> ForecastDate.Day(timeFormatter.formatDayWithDayOfWeek(this))
    }

    private fun ForecastEntry.toForecastCardState(
        timeFormatter: TimeFormatter,
    ): ForecastHourState =
        ForecastHourState(
            id = zonedDateTime.toEpochSecond(),
            time = timeFormatter.formatHour(zonedDateTime),
            description = description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            },
            iconId = weatherIconFromCode(iconCode).drawableId,
            temperature = temperature,
            feelsLikeTemperature = feelsLike,
            precipitation = precipitation,
            uvIndex = uvIndex,
            windSpeed = windSpeed,
            humidity = humidity,
            visibility = visibility,
        )

    private fun Current.toWeatherCardState() =
        CurrentWeatherState(
            temperature = temperature,
            feelsLikeTemperature = feelsLike,
            precipitation = precipitation,
            uvIndex = uvIndex,
            description = description.formatDescription(),
            windSpeed = wind,
            gustSpeed = gust,
            humidity = humidity,
            pressure = pressure,
            visibility = visibility,
            iconId = weatherIconFromCode(iconCode).drawableId,
        )

    private fun String.formatDescription(): String =
        replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
}
