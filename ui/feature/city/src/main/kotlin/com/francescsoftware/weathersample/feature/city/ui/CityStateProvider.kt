package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.feature.city.model.CityResultModel

internal val VancouverCityModel = CityResultModel(
    id = 1L,
    name = "Vancouver",
    country = "Canada",
    countryCode = "CA",
    coordinates = "Lat: 49.26, Lon: -123.11"
)

internal val BarcelonaCityModel = CityResultModel(
    id = 2L,
    name = "Barcelona",
    country = "Spain",
    countryCode = "ES",
    coordinates = "Lat: 41.39, Lon: 2.17"
)

internal val LondonCityModel = CityResultModel(
    id = 3L,
    name = "London",
    country = "England",
    countryCode = "UK",
    coordinates = "Lat: 123.45, Lon: 1.23"
)

internal class CityStateProvider : PreviewParameterProvider<CityResultModel> {
    override val values: Sequence<CityResultModel> = sequenceOf(
        VancouverCityModel,
        BarcelonaCityModel,
        LondonCityModel,
    )
}
