package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.ForecastItem
import com.francescsoftware.weathersample.feature.weather.R
import com.francescsoftware.weathersample.feature.weather.WeatherAction
import com.francescsoftware.weathersample.feature.weather.WeatherIcon
import com.francescsoftware.weathersample.feature.weather.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.WeatherState
import com.francescsoftware.weathersample.feature.weather.formatHumidity
import com.francescsoftware.weathersample.feature.weather.formatTemperature
import com.francescsoftware.weathersample.feature.weather.formatVisibility
import com.francescsoftware.weathersample.feature.weather.formatWind
import com.francescsoftware.weathersample.feature.weather.toWeatherCardState
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.shared.mvi.ActionHandler
import com.francescsoftware.weathersample.shared.mvi.Middleware
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

internal class WeatherMiddleware @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : Middleware<WeatherState, WeatherAction> {

    private lateinit var actionHandler: ActionHandler<WeatherAction>
    private lateinit var scope: CoroutineScope

    override fun setup(scope: CoroutineScope, actionHandler: ActionHandler<WeatherAction>) {
        this.actionHandler = actionHandler
        this.scope = scope
    }

    override fun reduce(
        state: WeatherState,
        action: WeatherAction,
    ): WeatherState = when (action) {
        is WeatherAction.Retry -> scope.onLoad(state, action.cityName, action.countryCode)
        is WeatherAction.Load -> scope.onLoad(state, action.cityName, action.countryCode)
        else -> state
    }

    private fun CoroutineScope.onLoad(
        state: WeatherState,
        cityName: String,
        countryCode: String,
    ): WeatherState {
        launch {
            load(
                name = cityName,
                countryCode = countryCode,
            )
        }
        return state.copy(
            loadState = WeatherLoadState.Loading,
        )
    }

    private suspend fun load(
        name: String,
        countryCode: String,
    ) {
        coroutineScope {
            val location = WeatherLocation.City(
                name = name,
                countryCode = countryCode,
            )
            val currentAsync = async { getTodayWeatherInteractor.execute(location) }
            val forecastAsync = async { getForecastInteractor.execute(location) }
            val current = currentAsync.await().getOrNull()
            val forecast = forecastAsync.await().getOrNull()
            if (current != null && forecast != null) {
                actionHandler.handleAction(
                    WeatherAction.Loaded(
                        currentWeather = current.toWeatherCardState(stringLookup),
                        forecastItems = forecast.toForecastItems()
                    )
                )
            } else {
                actionHandler.handleAction(
                    WeatherAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        }
    }

    private fun Forecast.toForecastItems(): List<ForecastItem> = items.map { forecastDay ->
        val header = forecastDay.toForecastHeaderState()
        val content = forecastDay.entries.map { entry -> entry.toForecastCardState() }
        listOf(header) + content
    }.flatten()

    private fun ForecastDay.toForecastHeaderState(): ForecastItem.ForecastHeader =
        ForecastItem.ForecastHeader(
            id = date.time,
            date = date.toHeaderLabel(),
            sunrise = timeFormatter.formatHour(sunrise),
            sunset = timeFormatter.formatHour(sunset),
        )

    private fun Date.toHeaderLabel(): String = when {
        isToday -> stringLookup.getString(R.string.today)
        isTomorrow -> stringLookup.getString(R.string.tomorrow)
        else -> timeFormatter.formatDayWithDayOfWeek(this)
    }

    private fun ForecastEntry.toForecastCardState(): ForecastItem.ForecastCard =
        ForecastItem.ForecastCard(
            id = date.time,
            header = stringLookup.getString(
                R.string.forecast_card_header,
                timeFormatter.formatHour(date),
                description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            ),
            iconId = WeatherIcon.fromIconId(icon).iconId,
            minTemperature = minTemperature.formatTemperature(stringLookup),
            maxTemperature = maxTemperature.formatTemperature(stringLookup),
            feelsLikeTemperature = feelsLikeTemperature.formatTemperature(stringLookup),
            windSpeed = windSpeed.formatWind(stringLookup),
            humidity = humidityPercent.formatHumidity(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
        )
}
