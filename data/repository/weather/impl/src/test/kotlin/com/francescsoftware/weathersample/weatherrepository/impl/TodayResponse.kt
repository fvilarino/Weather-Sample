package com.francescsoftware.weathersample.weatherrepository.impl

internal const val TodayResponse = """
    {
    "location": {
        "name": "Vancouver",
        "region": "British Columbia",
        "country": "Canada",
        "lat": 49.25,
        "lon": -123.13,
        "tz_id": "America/Vancouver",
        "localtime_epoch": 1683578525,
        "localtime": "2023-05-08 13:42"
    },
    "current": {
        "last_updated_epoch": 1683577800,
        "last_updated": "2023-05-08 13:30",
        "temp_c": 14.0,
        "temp_f": 57.2,
        "is_day": 1,
        "condition": {
            "text": "Partly cloudy",
            "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png",
            "code": 1003
        },
        "wind_mph": 9.4,
        "wind_kph": 15.1,
        "wind_degree": 240,
        "wind_dir": "WSW",
        "pressure_mb": 1015.0,
        "pressure_in": 29.98,
        "precip_mm": 0.0,
        "precip_in": 0.0,
        "humidity": 82,
        "cloud": 75,
        "feelslike_c": 13.4,
        "feelslike_f": 56.1,
        "vis_km": 48.0,
        "vis_miles": 29.0,
        "uv": 4.0,
        "gust_mph": 7.2,
        "gust_kph": 11.5
    }
}
"""
