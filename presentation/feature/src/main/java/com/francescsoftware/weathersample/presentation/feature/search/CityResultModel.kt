package com.francescsoftware.weathersample.presentation.feature.search

import com.francescsoftware.weathersample.presentation.shared.recyclerview.Diffable

data class CityResultModel(
    override val id: Int,
    val name: CharSequence,
    val country: CharSequence,
    val countryCode: String,
    val coordinates: CharSequence,
): Diffable {
    lateinit var clickCallback: (CityResultModel) -> Unit

    fun onClick() = clickCallback(this)
}
