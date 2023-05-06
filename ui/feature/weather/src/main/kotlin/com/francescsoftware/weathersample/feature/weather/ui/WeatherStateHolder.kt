package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

internal enum class SelectedWeatherOption(val value: Int) {
    Today(0),
    Forecast(1);

    companion object {
        fun fromValue(value: Int) = values().firstOrNull { option -> option.value == value } ?: Today
    }
}

@Stable
internal interface WeatherStateHolder {
    val option: SelectedWeatherOption
    fun onOptionSelect(option: SelectedWeatherOption)
}

@Composable
internal fun rememberWeatherStateHolder(
    option: SelectedWeatherOption = SelectedWeatherOption.Today,
): WeatherStateHolder = rememberSaveable(saver = WeatherStateHolderImpl.Saver) {
    WeatherStateHolderImpl(option)
}

@Stable
private class WeatherStateHolderImpl(
    option: SelectedWeatherOption,
) : WeatherStateHolder {
    override var option: SelectedWeatherOption by mutableStateOf(option)
        private set

    override fun onOptionSelect(option: SelectedWeatherOption) {
        this.option = option
    }

    companion object {
        val Saver = Saver<WeatherStateHolderImpl, List<Any>>(
            save = {
                listOf(it.option.value)
            },
            restore = { state ->
                WeatherStateHolderImpl(SelectedWeatherOption.fromValue(state[0] as Int))
            }
        )
    }
}
