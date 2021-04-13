package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.interactor.weather.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.GetForecastInteractor
import com.francescsoftware.weathersample.interactor.weather.WeatherLocation
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.WeatherIcon
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatHumidity
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatTemperature
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatVisibility
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.formatWind
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.presentation.shared.recyclerview.RecyclerViewBindingItem
import com.francescsoftware.weathersample.utils.time.TimeFormatter
import com.francescsoftware.weathersample.utils.time.isToday
import com.francescsoftware.weathersample.utils.time.isTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastInteractor: GetForecastInteractor,
    private val timeFormatter: TimeFormatter,
    private val stringLookup: StringLookup,
) : MviViewModel<ForecastState, ForecastEvent, ForecastMviIntent, ForecastReduceAction>(
    ForecastState.initial
), DefaultLifecycleObserver {

    private var selectedCity: SelectedCity? = null

    override suspend fun executeIntent(intent: ForecastMviIntent) {
        when (intent) {
            is ForecastMviIntent.Load -> {
                selectedCity = intent.selectedCity
                load()
            }
            ForecastMviIntent.Retry -> load()
        }
    }

    override fun reduce(state: ForecastState, reduceAction: ForecastReduceAction): ForecastState =
        when (reduceAction) {
            ForecastReduceAction.Loading -> state.copy(
                loadState = ForecastLoadState.LOADING,
                errorMessage = "",
            )
            is ForecastReduceAction.Loaded -> state.copy(
                loadState = ForecastLoadState.LOADED,
                forecast = reduceAction.items,
                errorMessage = "",
            )
            is ForecastReduceAction.LoadError -> state.copy(
                loadState = ForecastLoadState.ERROR,
                errorMessage = stringLookup.getString(R.string.failed_to_load_forecast)
            )
        }

    private suspend fun load() {
        val city = selectedCity
        check(city != null) { "Invalid state, selected city is null" }
        handle(ForecastReduceAction.Loading)
        val result = getForecastInteractor.execute(
            WeatherLocation.City(
                name = city.name,
                countryCode = city.countryCode,
            )
        )
        result.fold(
            onSuccess = { forecast ->
                val items: List<RecyclerViewBindingItem> = forecast.items.map { forecastDay ->
                    val header = forecastDay.toHeaderBindingItem()
                    val content = forecastDay.entries.map { entry -> entry.toForecastBindingItem() }
                    listOf(header) + content
                }.flatten()
                handle(
                    ForecastReduceAction.Loaded(
                        items = items
                    )
                )
            },
            onFailure = {
                handle(
                    ForecastReduceAction.LoadError(
                        message = stringLookup.getString(R.string.failed_to_load_weather_data)
                    )
                )
            }
        )
    }

    private fun ForecastDay.toHeaderBindingItem(): RecyclerViewBindingItem =
        ForecastHeaderBindingItem(
            forecastHeaderState = ForecastHeaderState(
                id = date.time,
                date = date.toHeaderLabel(),
                sunrise = timeFormatter.formatHour(sunrise),
                sunset = timeFormatter.formatHour(sunset),
            )
        )

    private fun Date.toHeaderLabel(): CharSequence = when {
        isToday -> stringLookup.getString(R.string.today)
        isTomorrow -> stringLookup.getString(R.string.tomorrow)
        else -> timeFormatter.formatDay(this)
    }

    private fun ForecastEntry.toForecastBindingItem(): RecyclerViewBindingItem =
        ForecastCardBindingItem(
            forecastCardState = ForecastCardState(
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
        )
}
