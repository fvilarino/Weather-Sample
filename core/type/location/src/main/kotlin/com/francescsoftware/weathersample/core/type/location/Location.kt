package com.francescsoftware.weathersample.core.type.location

import kotlin.math.absoluteValue

private const val Epsilon = 1e-5

/**
 * Coordinates
 *
 * @property longitude longitude, in range -180 to 180
 * @property latitude latitude, in range -90 to 90
 */
class Coordinates(
    val longitude: Double,
    val latitude: Double,
) {
    val asIso6709: String
        get() = buildString {
            if (latitude >= 0.0) append("+")
            append(latitude.toString())
            if (longitude >= 0.0) append("+")
            append(longitude.toString())
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinates

        if ((longitude - other.longitude).absoluteValue > Epsilon) return false
        if ((latitude - other.latitude).absoluteValue > Epsilon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }
}
