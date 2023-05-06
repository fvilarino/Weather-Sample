package com.francescsoftware.weathersample.weatherrepository.api.model

/**
 * Weather condition
 *
 * @property code code identifying this condition
 * @property icon weather icon associated with the condition
 * @property text condition description
 */
data class Condition(
    val code: Int,
    val icon: String,
    val text: String,
)
