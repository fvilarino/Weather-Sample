package com.francescsoftware.weathersample.core.type.weather

private const val InchesToMillimeters = 25.4

@JvmInline
value class Precipitation private constructor(private val value: Double) {

    val millimeters: Double
        get() = value

    val inches: Double
        get() = value / InchesToMillimeters

    companion object {
        fun fromMillimeters(value: Double) = Precipitation(value)
        fun fromInches(value: Double) = Precipitation(value * InchesToMillimeters)
    }
}
