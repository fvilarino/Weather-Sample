@file:Suppress("TooManyFunctions")

package com.francescsoftware.weathersample.data.repository.weather.impl

import com.francescsoftware.weathersample.data.repository.weather.api.model.Condition
import com.francescsoftware.weathersample.data.repository.weather.api.model.Current
import com.francescsoftware.weathersample.data.repository.weather.api.model.Location
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Astro
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Day
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Forecast
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastDay
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastHour
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse
import com.francescsoftware.weathersample.data.repository.weather.impl.model.ConditionModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.CurrentModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.LocationModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.AstroModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.DayModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.ForecastDayModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.ForecastDaysModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.ForecastModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast.HourModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.today.TodayWeatherModel

internal fun TodayWeatherModel.toWeatherResponse(): TodayWeatherResponse = TodayWeatherResponse(
    current = requireNotNull(current).toCurrent(),
    location = requireNotNull(location).toLocation(),
)

@Suppress("CyclomaticComplexMethod")
internal fun CurrentModel.toCurrent(): Current = Current(
    feelsLikeCelsius = feelsLikeC ?: 0.0,
    uvIndex = uv ?: 0.0,
    lastUpdated = lastUpdated.orEmpty(),
    feelsLikeFahrenheit = feelsLikeF ?: 0.0,
    windDegree = windDegree ?: 0,
    lastUpdatedEpoch = lastUpdatedEpoch ?: 0,
    isDay = isDay ?: 0,
    precipitationInches = precipIn ?: 0.0,
    windDirection = windDir.orEmpty(),
    gustMph = gustMph ?: 0.0,
    tempCelsius = tempC ?: 0.0,
    pressureIn = precipIn ?: 0.0,
    gustKph = gustKph ?: 0.0,
    tempFahrenheit = tempF ?: 0.0,
    precipitationMm = precipMm ?: 0.0,
    cloud = cloud ?: 0,
    windKph = windKph ?: 0.0,
    condition = requireNotNull(condition).toCondition(),
    windMph = windMph ?: 0.0,
    visibilityKm = visKm ?: 0.0,
    humidity = humidity ?: 0,
    pressureMb = pressureMb ?: 0.0,
    visibilityMiles = visMiles ?: 0.0,
)

internal fun ConditionModel.toCondition(): Condition = Condition(
    code = code ?: 0,
    icon = icon.orEmpty(),
    text = text.orEmpty(),
)

internal fun LocationModel.toLocation(): Location = Location(
    localtime = localtime.orEmpty(),
    localtimeEpoch = localtimeEpoch ?: 0,
    name = name.orEmpty(),
    region = region.orEmpty(),
    country = country.orEmpty(),
    latitude = lat ?: 0.0,
    longitude = lon ?: 0.0,
    timezoneId = tzId.orEmpty(),
)

internal fun ForecastModel.toForecastResponse(): ForecastResponse = ForecastResponse(
    current = requireNotNull(current).toCurrent(),
    location = requireNotNull(location).toLocation(),
    forecast = requireNotNull(forecast).toForecast(),
)

internal fun ForecastDaysModel.toForecast(): Forecast = Forecast(
    forecastDay = requireNotNull(forecastDay).toForecast()
)

internal fun List<ForecastDayModel>.toForecast(): List<ForecastDay> = map { forecast ->
    forecast.toForecast()
}

internal fun ForecastDayModel.toForecast(): ForecastDay = ForecastDay(
    date = date.orEmpty(),
    dateEpoch = dateEpoch ?: 0,
    day = requireNotNull(day).toDay(),
    hour = requireNotNull(hour).toHour(),
    astro = requireNotNull(astro).toAstro(),
)

@Suppress("CyclomaticComplexMethod")
internal fun DayModel.toDay(): Day = Day(
    condition = requireNotNull(condition).toCondition(),
    averageTempCelsius = avgtempC ?: 0.0,
    averageTempFahrenheit = avgtempF ?: 0.0,
    maxTempCelsius = maxtempC ?: 0.0,
    maxTempFahrenheit = maxtempF ?: 0.0,
    minTempCelsius = mintempC ?: 0.0,
    minTempFahrenheit = mintempF ?: 0.0,
    dailyChanceOfRain = dailyChanceOfRain ?: 0,
    dailyWillItRain = dailyWillItRain ?: 0,
    totalPrecipitationMm = totalprecipMm ?: 0.0,
    totalPrecipitationIn = totalprecipIn ?: 0.0,
    dailyWillItSnow = dailyWillItSnow ?: 0,
    dailyChanceOfSnow = dailyChanceOfSnow ?: 0,
    averageHumidity = avghumidity ?: 0.0,
    maxWindKph = maxwindKph ?: 0.0,
    maxWindMph = maxwindMph ?: 0.0,
    averageVisibilityKm = avgvisKm ?: 0.0,
    averageVisibilityMiles = avgvisMiles ?: 0.0,
    uvIndex = uv ?: 0.0,
)

internal fun List<HourModel>.toHour(): List<ForecastHour> = map { hour ->
    hour.toHour()
}

@Suppress("CyclomaticComplexMethod")
internal fun HourModel.toHour(): ForecastHour = ForecastHour(
    isDay = isDay ?: 0,
    time = time.orEmpty(),
    timeEpoch = timeEpoch ?: 0,
    tempCelsius = tempC ?: 0.0,
    tempFahrenheit = tempF ?: 0.0,
    feelsLikeCelsius = feelslikeC ?: 0.0,
    feelsLikeFahrenheit = feelslikeF ?: 0.0,
    windChillCelsius = windchillC ?: 0.0,
    willChillFahrenheit = windchillF ?: 0.0,
    windDirection = windDir.orEmpty(),
    windDegree = windDegree ?: 0,
    windKph = windKph ?: 0.0,
    windMph = windMph ?: 0.0,
    gustKph = gustKph ?: 0.0,
    gustMph = gustMph ?: 0.0,
    cloud = cloud ?: 0,
    humidity = humidity ?: 0,
    dewPointCelsius = dewpointC ?: 0.0,
    dewPointFahrenheit = dewpointF ?: 0.0,
    uvIndex = uv ?: 0.0,
    headIndexCelsius = heatindexC ?: 0.0,
    heatIndexFahrenheit = heatindexF ?: 0.0,
    willItRain = willItRain ?: 0,
    chanceOfRain = chanceOfRain ?: 0,
    precipitationMm = precipMm ?: 0.0,
    precipitationInches = precipIn ?: 0.0,
    condition = requireNotNull(condition).toCondition(),
    willItSnow = willItSnow ?: 0,
    chanceOfSnow = chanceOfSnow ?: 0,
    pressureMb = pressureMb ?: 0.0,
    pressureIn = pressureIn ?: 0.0,
    visibilityKm = visKm ?: 0.0,
    visibilityMiles = visMiles ?: 0.0,
)

internal fun AstroModel.toAstro(): Astro = Astro(
    sunrise = sunrise.orEmpty(),
    sunset = sunset.orEmpty(),
    moonrise = moonrise.orEmpty(),
    moonset = moonset.orEmpty(),
    moonIllumination = moonIllumination.orEmpty(),
    moonPhase = moonPhase.orEmpty(),
)
