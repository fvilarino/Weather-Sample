package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.lookup.api.StringLookup
import com.francescsoftware.weathersample.mvi.MviViewModel
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.navigator.NavigationDestination
import com.francescsoftware.weathersample.storage.city.api.SelectedCity
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.fold
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

interface WeatherCallbacks {
    fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen)
    fun refreshTodayWeather()
    fun retry()
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : MviViewModel<TodayState, TodayEvent, TodayMviIntent, TodayReduceAction>(
    TodayState.initial,
), WeatherCallbacks {

    private val selectedCity: SelectedCity = NavigationDestination.Weather.getCity(savedStateHandle)

    init {
        viewModelScope.launch { load() }
    }

    override fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen) {
        onIntent(TodayMviIntent.OnOptionSelected(selectedWeatherScreen))
    }

    override fun refreshTodayWeather() {
        onIntent(TodayMviIntent.RefreshTodayWeather)
    }

    override fun retry() {
        onIntent(TodayMviIntent.Retry)
    }

    override suspend fun executeIntent(intent: TodayMviIntent) {
        when (intent) {
            TodayMviIntent.RefreshTodayWeather -> loadTodayWeather()
            TodayMviIntent.Retry -> load()
            is TodayMviIntent.OnOptionSelected -> handle(
                TodayReduceAction.OnOptionSelected(intent.option)
            )
        }
    }

    override fun reduce(state: TodayState, reduceAction: TodayReduceAction): TodayState =
        when (reduceAction) {
            is TodayReduceAction.CityUpdated -> state.copy(
                cityName = reduceAction.name,
                cityCountryCode = reduceAction.countryCode,
            )
            TodayReduceAction.Loading -> state.copy(
                loadState = WeatherLoadState.LOADING,
            )
            TodayReduceAction.Refreshing -> state.copy(
                loadState = WeatherLoadState.REFRESHING,
            )
            is TodayReduceAction.Loaded -> state.copy(
                loadState = WeatherLoadState.LOADED,
                todayState = reduceAction.currentWeather,
                forecastItems = reduceAction.forecastItems,
            )
            is TodayReduceAction.TodayLoaded -> state.copy(
                loadState = WeatherLoadState.LOADED,
                todayState = reduceAction.currentWeather,
            )
            is TodayReduceAction.LoadError -> state.copy(
                loadState = WeatherLoadState.ERROR,
                errorMessage = reduceAction.message,
            )
            is TodayReduceAction.OnOptionSelected -> state.copy(
                option = reduceAction.option
            )
        }

    private fun loadTodayWeather() {
        handle(TodayReduceAction.Refreshing)
        onBackground {
            val location = WeatherLocation.City(
                name = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
            getTodayWeatherInteractor.execute(location).fold(
                onSuccess = { todayWeather ->
                    handle(
                        TodayReduceAction.TodayLoaded(
                            currentWeather = todayWeather.toWeatherCardState(),
                        )
                    )
                },
                onFailure = {
                    handle(
                        TodayReduceAction.LoadError(
                            message = stringLookup.getString(R.string.failed_to_load_weather_data)
                        )
                    )
                }
            )
        }
    }

    private fun load() {
        handle(
            TodayReduceAction.CityUpdated(
                name = selectedCity.name,
                countryCode = selectedCity.countryCode
            )
        )
        handle(TodayReduceAction.Loading)
        onBackground {
            val location = WeatherLocation.City(
                name = selectedCity.name,
                countryCode = selectedCity.countryCode,
            )
            val currentAsync = async { getTodayWeatherInteractor.execute(location) }
            val forecastAsync = async { getForecastInteractor.execute(location) }
            val current = currentAsync.await().getOrNull()
            val forecast = forecastAsync.await().getOrNull()
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

    private val TodayWeather.icon: Int
        get() = WeatherIcon.fromIconId(weather.icon).iconId
}
