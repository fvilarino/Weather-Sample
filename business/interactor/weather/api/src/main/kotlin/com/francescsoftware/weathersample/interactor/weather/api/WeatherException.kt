package com.francescsoftware.weathersample.interactor.weather.api

import java.io.IOException

/** Exception thrown when fetching weather data */
class WeatherException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause)
