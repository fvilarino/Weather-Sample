package com.francescsoftware.weathersample.presentation.feature.weather.tabs.today

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.interactor.weather.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.interactor.weather.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.WeatherLocation
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.WeatherIcon
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatDescription
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatHumidity
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatPressure
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatTemperature
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatVisibility
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatWind
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.type.fold
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
                currentTemperature = todayWeather.main.temp.formatTemperature(stringLookup),
                minTemperature = todayWeather.main.tempMin.formatTemperature(stringLookup),
                maxTemperature = todayWeather.main.tempMax.formatTemperature(stringLookup),
                feelsLikeTemperature = todayWeather.main.feelsLike.formatTemperature(stringLookup),
                description = todayWeather.formatDescription(),
                windSpeed = todayWeather.wind.speed.formatWind(stringLookup),
                humidity = todayWeather.main.humidity.formatHumidity(stringLookup),
                pressure = todayWeather.main.pressure.formatPressure(stringLookup),
                visibility = todayWeather.visibility.formatVisibility(stringLookup),
                iconId = todayWeather.icon,
            )
        )
    }

    private val TodayWeather.icon: Int
        get() = WeatherIcon.fromIconId(weather.icon).iconId
}
