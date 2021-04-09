package com.francescsoftware.weathersample.business.interactor.weather

import java.io.IOException

class WeatherException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
