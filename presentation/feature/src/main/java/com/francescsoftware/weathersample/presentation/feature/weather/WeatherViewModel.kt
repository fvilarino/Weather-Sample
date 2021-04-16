package com.francescsoftware.weathersample.presentation.feature.weather

import com.francescsoftware.weathersample.interactor.weather.Forecast
import com.francescsoftware.weathersample.interactor.weather.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.WeatherLocation
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.utils.time.TimeFormatter
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

interface WeatherCallbacks {
    fun onOptionSelect(weatherSelectorOptions: WeatherSelectorOptions)
    fun retry()
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : MviViewModel<TodayState, TodayEvent, TodayMviIntent, TodayReduceAction>(
    TodayState.initial
), WeatherCallbacks {

    private var selectedCity: SelectedCity? = null

    override fun onOptionSelect(weatherSelectorOptions: WeatherSelectorOptions) {
        onIntent(TodayMviIntent.OnOptionSelected(weatherSelectorOptions))
    }

    override fun retry() {
        onIntent(TodayMviIntent.Retry)
    }

    override suspend fun executeIntent(intent: TodayMviIntent) {
        when (intent) {
            is TodayMviIntent.Load -> {
                selectedCity = intent.selectedCity
                load()
            }
            TodayMviIntent.Retry -> load()
            is TodayMviIntent.OnOptionSelected -> handle(
                TodayReduceAction.OnOptionSelected(intent.option)
            )
        }
    }

    override fun reduce(state: TodayState, reduceAction: TodayReduceAction): TodayState =
        when (reduceAction) {
            TodayReduceAction.Loading -> state.copy(
                loadState = WeatherLoadState.LOADING,
            )
            is TodayReduceAction.Loaded -> state.copy(
                loadState = WeatherLoadState.LOADED,
                todayState = reduceAction.currentWeather,
                forecastItems = reduceAction.forecastItems,
            )
            is TodayReduceAction.LoadError -> state.copy(
                loadState = WeatherLoadState.ERROR,
                errorMessage = reduceAction.message,
            )
            is TodayReduceAction.OnOptionSelected -> state.copy(
                option = reduceAction.option
            )
        }

    private suspend fun load() {
        val city = selectedCity
        check(city != null) { "Invalid state, selected city is null" }
        handle(TodayReduceAction.Loading)
        val location = WeatherLocation.City(
            name = city.name,
            countryCode = city.countryCode,
        )
        val current = getTodayWeatherInteractor.execute(location).getOrNull()
        val forecast = getForecastInteractor.execute(location).getOrNull()
        if (current != null && forecast != null) {
            handle(
                TodayReduceAction.Loaded(
                    currentWeather = current.toWeatherCardState(),
                    forecastItems = forecast.toForecastItems()
                )
            )
        } else {
            handle(
                TodayReduceAction.LoadError(
                    message = stringLookup.getString(R.string.failed_to_load_weather_data)
                )
            )
        }
    }

    private fun Forecast.toForecastItems(): List<ForecastItem> = items.map { forecastDay ->
        val header = forecastDay.toForecastHeaderState()
        val content = forecastDay.entries.map { entry -> entry.toForecastCardState() }
        listOf(header) + content
    }.flatten()

    private fun TodayWeather.toWeatherCardState() =
        TodayWeatherCardState(
            temperature = main.temp.formatTemperature(stringLookup),
            minTemperature = main.tempMin.formatTemperature(stringLookup),
            maxTemperature = main.tempMax.formatTemperature(stringLookup),
            feelsLikeTemperature = main.feelsLike.formatTemperature(stringLookup),
            description = formatDescription(),
            windSpeed = wind.speed.formatWind(stringLookup),
            humidity = main.humidity.formatHumidity(stringLookup),
            pressure = main.pressure.formatPressure(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
            iconId = icon,
        )

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
                description.capitalize(Locale.getDefault())
            ),
            iconId = WeatherIcon.fromIconId(icon).iconId,
            minTemperature = minTemperature.formatTemperature(stringLookup),
            maxTemperature = maxTemperature.formatTemperature(stringLookup),
            feelsLikeTemperature = feelsLikeTemperature.formatTemperature(stringLookup),
            windSpeed = windSpeed.formatWind(stringLookup),
            humidity = humidityPercent.formatHumidity(stringLookup),
            visibility = visibility.formatVisibility(stringLookup),
        )

    private val TodayWeather.icon: Int
        get() = WeatherIcon.fromIconId(weather.icon).iconId
}
