package com.francescsoftware.weathersample.data.repository.weather.api

import java.io.IOException

/**
 * Exception thrown when fetching the weather
 *
 * @param message the exception message
 * @param cause the cause of the exception
 */
class WeatherException(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause)
