package com.francescsoftware.weathersample.core.type.weather

@JvmInline
value class Temperature private constructor(private val value: Double) {

    val celsius: Double
        get() = value

    val fahrenheit: Double
        get() = (value * 1.8) + 32.0

    companion object {
        fun fromCelsius(value: Double) = Temperature(value)
        fun fromFahrenheit(value: Double) = Temperature((value - 32.0) / 1.8)
    }
}
