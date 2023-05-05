package com.francescsoftware.weathersample.cityrepository.api

import java.io.IOException

/**
 * Exception thrown when fetching cities
 *
 * @param message the exception message
 * @param cause the cause of the exception
 */
class CitiesException(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause)
