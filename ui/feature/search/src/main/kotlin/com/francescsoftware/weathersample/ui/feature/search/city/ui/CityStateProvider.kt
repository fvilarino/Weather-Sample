package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Coordinates

internal val VancouverCityModel = City(
    id = 1L,
    name = "Vancouver",
    region = "British Columbia",
    regionCode = "BC",
    country = "Canada",
    countryCode = "CA",
    coordinates = Coordinates(latitude = 49.26, longitude = -123.11),
)

internal val BarcelonaCityModel = City(
    id = -1L,
    name = "Barcelona",
    region = "Catalunya",
    regionCode = "CA",
    country = "Spain",
    countryCode = "ES",
    coordinates = Coordinates(latitude = 41.39, longitude = 2.17),
)

internal val LondonCityModel = City(
    id = -1L,
    name = "London",
    region = "London",
    regionCode = "LO",
    country = "England",
    countryCode = "UK",
    coordinates = Coordinates(latitude = 51.51, longitude = -0.12),
)

internal class CityStateProvider : PreviewParameterProvider<City> {
    override val values: Sequence<City> = sequenceOf(
        VancouverCityModel,
        BarcelonaCityModel,
        LondonCityModel,
    )
}
