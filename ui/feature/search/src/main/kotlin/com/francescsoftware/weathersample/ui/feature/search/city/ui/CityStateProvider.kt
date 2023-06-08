package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.Coordinates

internal val VancouverCityModel = CityResultModel(
    id = 1L,
    favoriteId = 1,
    name = "Vancouver",
    country = "Canada",
    countryCode = "CA",
    coordinates = Coordinates(latitude = 49.26f, longitude = -123.11f),
)

internal val BarcelonaCityModel = CityResultModel(
    id = 2L,
    favoriteId = -1,
    name = "Barcelona",
    country = "Spain",
    countryCode = "ES",
    coordinates = Coordinates(latitude = 41.39f, longitude = 2.17f),
)

internal val LondonCityModel = CityResultModel(
    id = 3L,
    favoriteId = -1,
    name = "London",
    country = "England",
    countryCode = "UK",
    coordinates = Coordinates(latitude = 51.51f, longitude = -0.12f),
)

internal class CityStateProvider : PreviewParameterProvider<CityResultModel> {
    override val values: Sequence<CityResultModel> = sequenceOf(
        VancouverCityModel,
        BarcelonaCityModel,
        LondonCityModel,
    )
}
