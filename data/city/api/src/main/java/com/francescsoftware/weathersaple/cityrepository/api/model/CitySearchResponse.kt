package com.francescsoftware.weathersaple.cityrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitySearchResponse(

    @SerialName("metadata")
    val metadata: Metadata? = null,

    @SerialName("data")
    val data: List<CityItem>? = null,

    @SerialName("links")
    val links: List<LinksItem>? = null
)

@Serializable
data class LinksItem(

    @SerialName("rel")
    val rel: String? = null,

    @SerialName("href")
    val href: String? = null
)

@Serializable
data class Metadata(

    @SerialName("currentOffset")
    val currentOffset: Int? = null,

    @SerialName("totalCount")
    val totalCount: Int? = null
)

@Serializable
data class CityItem(

    @SerialName("country")
    val country: String? = null,

    @SerialName("wikiDataId")
    val wikiDataId: String? = null,

    @SerialName("regionCode")
    val regionCode: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("countryCode")
    val countryCode: String? = null,

    @SerialName("latitude")
    val latitude: Double? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("region")
    val region: String? = null,

    @SerialName("longitude")
    val longitude: Double? = null
)
