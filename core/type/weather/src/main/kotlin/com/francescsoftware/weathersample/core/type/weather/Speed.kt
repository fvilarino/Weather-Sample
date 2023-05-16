package com.francescsoftware.weathersample.core.type.weather

private const val MilesPerKm = 1.604

@JvmInline
value class Speed private constructor(private val value: Double) {

    val kph: Double
        get() = value

    val mph: Double
        get() = value / MilesPerKm

    companion object {
        fun fromKph(value: Double) = Speed(value)
        fun fromMph(value: Double) = Speed(value * MilesPerKm)
    }
}
