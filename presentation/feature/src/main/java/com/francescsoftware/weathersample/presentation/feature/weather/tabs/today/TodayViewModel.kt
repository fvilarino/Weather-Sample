package com.francescsoftware.weathersample.presentation.feature.weather.tabs.today

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.WeatherLocation
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.WeatherIcon
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getTodayWeatherInteractor: GetTodayWeatherInteractor,
    private val stringLookup: StringLookup,
) :
    MviViewModel<TodayState, TodayEvent, TodayMviIntent, TodayReduceAction>(
        TodayState.initial
    ),
    DefaultLifecycleObserver {

    private var selectedCity: SelectedCity? = null

    override suspend fun executeIntent(intent: TodayMviIntent) {
        when (intent) {
            is TodayMviIntent.Load -> {
                selectedCity = intent.selectedCity
                load()
            }
            TodayMviIntent.Retry -> load()
        }
    }

    override fun reduce(state: TodayState, reduceAction: TodayReduceAction): TodayState =
        when (reduceAction) {
            TodayReduceAction.Loading -> state.copy(
                loadState = TodayLoadState.LOADING,
            )
            is TodayReduceAction.Loaded -> state.copy(
                loadState = TodayLoadState.LOADED,
                todayState = TodayWeatherCardState(
                    temperature = reduceAction.currentTemperature,
                    minTemperature = reduceAction.minTemperature,
                    maxTemperature = reduceAction.maxTemperature,
                    feelsLikeTemperature = reduceAction.feelsLikeTemperature,
                    description = reduceAction.description,
                    iconId = reduceAction.iconId,
                    windSpeed = reduceAction.windSpeed,
                    humidity = reduceAction.humidity,
                    pressure = reduceAction.pressure,
                    visibility = reduceAction.visibility,
                ),
            )
            is TodayReduceAction.LoadError -> state.copy(
                loadState = TodayLoadState.ERROR,
                errorMessage = reduceAction.message,
            )
        }

    private suspend fun load() {
        val city = selectedCity
        check(city != null) { "Invalid state, selected city is null" }
        handle(TodayReduceAction.Loading)
        val result = getTodayWeatherInteractor.execute(
            WeatherLocation.City(
                name = city.name,
                countryCode = city.countryCode,
            )
        )
        result.fold(
            onSuccess = { todayWeather ->
                onWeatherReceived(todayWeather)
            },
            onFailure = {
                handle(
                    TodayReduceAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            },
        )
    }

    private fun onWeatherReceived(todayWeather: TodayWeather) {
        handle(
            TodayReduceAction.Loaded(
                currentTemperature = todayWeather.main.temp.formatTemperature(),
                minTemperature = todayWeather.main.tempMin.formatTemperature(),
                maxTemperature = todayWeather.main.tempMax.formatTemperature(),
                feelsLikeTemperature = todayWeather.main.feelsLike.formatTemperature(),
                description = todayWeather.formatDescription(),
                windSpeed = todayWeather.formatWind(),
                humidity = todayWeather.formatHumidity(),
                pressure = todayWeather.formatPressure(),
                visibility = todayWeather.formatVisibility(),
                iconId = todayWeather.icon,
            )
        )
    }

    private fun Double.formatTemperature(): CharSequence = stringLookup.getString(
        R.string.formatted_temperature,
        this,
    )

    private fun TodayWeather.formatDescription(): CharSequence =
        weather.description.capitalize()

    private fun TodayWeather.formatWind(): CharSequence = stringLookup.getString(
        R.string.formatted_wind,
        wind.speed,
    )

    private fun TodayWeather.formatHumidity(): CharSequence = stringLookup.getString(
        R.string.formatted_humidity,
        main.humidity,
    )

    private fun TodayWeather.formatPressure(): CharSequence = stringLookup.getString(
        R.string.formatted_pressure,
        main.pressure,
    )

    private fun TodayWeather.formatVisibility(): CharSequence = stringLookup.getString(
        R.string.formatted_visibility,
        visibility,
    )

    private val TodayWeather.icon: Int
        get() = WeatherIcon.fromIconId(weather.icon).iconId
}
