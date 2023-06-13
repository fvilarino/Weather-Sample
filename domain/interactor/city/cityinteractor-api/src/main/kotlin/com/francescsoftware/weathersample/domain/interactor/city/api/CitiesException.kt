package com.francescsoftware.weathersample.domain.interactor.city.api

import java.io.IOException

/** Exception thrown when loading cities */
class CitiesException(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause)
