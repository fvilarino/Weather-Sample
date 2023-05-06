package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

/**
 * Astronomical information
 *
 * @property sunrise sun rise
 * @property sunset sun set
 * @property moonrise moon rise
 * @property moonset moon set
 * @property moonIllumination moon illumination
 * @property moonPhase moon phase
 */
data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moonIllumination: String,
    val moonPhase: String,
)
