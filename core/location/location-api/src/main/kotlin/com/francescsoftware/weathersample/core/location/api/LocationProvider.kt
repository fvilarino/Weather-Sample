package com.francescsoftware.weathersample.core.location.api

import com.francescsoftware.weathersample.core.type.location.Coordinates

interface LocationProvider {
    suspend fun getCurrentLocation(): Coordinates?
}
