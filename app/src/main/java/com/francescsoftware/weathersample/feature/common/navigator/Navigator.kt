package com.francescsoftware.weathersample.feature.common.navigator

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.feature.weather.SelectedCity

interface Navigator : DefaultLifecycleObserver {
    fun cityToWeather(city: SelectedCity)
    fun goBack()
}
