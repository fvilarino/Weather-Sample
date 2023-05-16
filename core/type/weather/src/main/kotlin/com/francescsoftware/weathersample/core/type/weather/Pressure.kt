package com.francescsoftware.weathersample.core.type.weather

private const val MbToInchesFactor = 33.864

@JvmInline
value class Pressure private constructor(private val value: Double) {

    val millibars: Double
        get() = value

    val hgInches: Double
        get() = value * MbToInchesFactor

    companion object {
        fun fromMillibars(value: Double) = Pressure(value)
        fun fromHgInches(value: Double) = Pressure(value / MbToInchesFactor)
    }
}

