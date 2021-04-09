package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.presentation.shared.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val stringLookup: StringLookup,
) :
    MviViewModel<WeatherState, WeatherEvent, WeatherIntent, WeatherReduceAction>(
        WeatherState.initial
    ),
    DefaultLifecycleObserver {

    private var selectedCity: SelectedCity? = null

    override suspend fun executeIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.CityParsed -> {
                selectedCity = intent.city
                handle(
                    WeatherReduceAction.UpdateCity(
                        stringLookup.getString(
                            R.string.weather_city_name,
                            intent.city.name,
                            intent.city.countryCode
                        )
                    )
                )
            }
        }
    }

    override fun reduce(state: WeatherState, reduceAction: WeatherReduceAction): WeatherState =
        when (reduceAction) {
            is WeatherReduceAction.UpdateCity -> state.copy(cityName = reduceAction.city)
        }
}
