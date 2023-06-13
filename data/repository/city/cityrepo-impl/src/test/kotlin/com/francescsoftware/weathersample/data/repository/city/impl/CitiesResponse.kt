package com.francescsoftware.weathersample.data.repository.city.impl

internal const val CitiesResponse = """
    {
        "data": [{
            "id": 3323992,
            "wikiDataId": "Q2781258",
            "type": "CITY",
            "city": "Milwich",
            "name": "Milwich",
            "country": "United Kingdom",
            "countryCode": "GB",
            "region": "Staffordshire",
            "regionCode": "STS",
            "regionWdId": "Q23105",
            "latitude": 52.8879,
            "longitude": -2.04314,
            "population": 0
        }, {
            "id": 3415353,
            "wikiDataId": "Q2683231",
            "type": "CITY",
            "city": "Milwino",
            "name": "Milwino",
            "country": "Poland",
            "countryCode": "PL",
            "region": "Pomeranian Voivodeship",
            "regionCode": "22",
            "regionWdId": "Q54180",
            "latitude": 54.51944,
            "longitude": 18.13167,
            "population": 586
        }, {
            "id": 3371102,
            "wikiDataId": "Q6861638",
            "type": "CITY",
            "city": "Milwaukee",
            "name": "Milwaukee",
            "country": "United States of America",
            "countryCode": "US",
            "region": "North Carolina",
            "regionCode": "NC",
            "regionWdId": "Q1454",
            "latitude": 36.4056,
            "longitude": -77.2322,
            "population": 157
        }],
        "metadata": {
            "currentOffset": 0,
            "totalCount": 3
        }
    }
"""

// missing name on 1st, country on 2nd and region code on 3rd
internal const val InvalidCitiesResponse = """
    {
        "data": [{
            "id": 3323992,
            "wikiDataId": "Q2781258",
            "type": "CITY",
            "city": "Milwich",
            "country": "United Kingdom",
            "countryCode": "GB",
            "region": "Staffordshire",
            "regionCode": "STS",
            "regionWdId": "Q23105",
            "latitude": 52.8879,
            "longitude": -2.04314,
            "population": 0
        }, {
            "id": 3415353,
            "wikiDataId": "Q2683231",
            "type": "CITY",
            "city": "Milwino",
            "name": "Milwino",
            "countryCode": "PL",
            "region": "Pomeranian Voivodeship",
            "regionCode": "22",
            "regionWdId": "Q54180",
            "latitude": 54.51944,
            "longitude": 18.13167,
            "population": 586
        }, {
            "id": 3371102,
            "wikiDataId": "Q6861638",
            "type": "CITY",
            "city": "Milwaukee",
            "name": "Milwaukee",
            "country": "United States of America",
            "countryCode": "US",
            "region": "North Carolina",
            "regionWdId": "Q1454",
            "latitude": 36.4056,
            "longitude": -77.2322,
            "population": 157
        }],
        "metadata": {
            "currentOffset": 0,
            "totalCount": 3
        }
    }
"""
