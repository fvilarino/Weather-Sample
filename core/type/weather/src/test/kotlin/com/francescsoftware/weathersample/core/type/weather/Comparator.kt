package com.francescsoftware.weathersample.core.type.weather

import kotlin.math.absoluteValue

internal fun compare(
    left: Double,
    right: Double,
    epsilon: Double = .001,
): Boolean = (left - right).absoluteValue <= epsilon
