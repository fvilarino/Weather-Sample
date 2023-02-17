package com.francescsoftware.weathersample.interactor.weather.api

import java.io.IOException

/** Exception thrown when fetching weather data */
class WeatherException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
