package com.francescsoftware.weathersample.core.type.weather

private const val MilesPerKm = 1.604

@JvmInline
value class AverageVisibility private constructor(private val value: Double) {
    val kilometers: Double
        get() = value

    val miles: Double
        get() = value / MilesPerKm

    companion object {
        fun fromKm(value: Double) = AverageVisibility(value)
        fun fromMiles(value: Double) = AverageVisibility(value * MilesPerKm)
    }
}
