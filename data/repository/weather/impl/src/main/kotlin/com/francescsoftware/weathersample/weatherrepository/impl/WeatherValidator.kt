package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.weatherrepository.impl.model.ConditionModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.CurrentModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.LocationModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.AstroModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.DayModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.ForecastDayModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.ForecastDaysModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.ForecastModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.forecast.HourModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.today.TodayWeatherModel

internal val TodayWeatherModel.isValid: Boolean
    get() = current.isValid

internal val CurrentModel?.isValid: Boolean
    get() = this != null &&
        condition.isValid &&
        tempC != null &&
        tempF != null &&
        feelsLikeC != null &&
        feelsLikeF != null &&
        humidity != null &&
        pressureMb != null &&
        windDir != null &&
        windKph != null &&
        gustKph != null &&
        visKm != null &&
        cloud != null

internal val ConditionModel?.isValid: Boolean
    get() = this != null && code != null && icon != null && text != null

internal val ForecastModel.isValid: Boolean
    get() = current?.isValid == true &&
        location?.isValid == true &&
        forecast?.isValid == true

internal val LocationModel.isValid: Boolean
    get() = localtime?.isNotEmpty() == true &&
        localtimeEpoch != null &&
        name?.isNotEmpty() == true &&
        region?.isNotEmpty() == true &&
        country?.isNotEmpty() == true &&
        lon != null &&
        lat != null &&
        tzId != null

internal val ForecastDaysModel.isValid: Boolean
    get() = forecastDay != null && forecastDay.all { day -> day.isValid }

internal val ForecastDayModel.isValid: Boolean
    get() = date?.isNotEmpty() == true &&
        dateEpoch != null &&
        astro?.isValid == true &&
        day?.isValid == true &&
        hour?.all { it.isValid } == true

internal val DayModel.isValid: Boolean
    get() = condition?.isValid == true &&
        avgtempC != null &&
        avgtempF != null &&
        maxtempC != null &&
        maxtempF != null &&
        mintempC != null &&
        mintempF != null &&
        dailyChanceOfRain != null &&
        dailyWillItRain != null &&
        totalprecipMm != null &&
        totalprecipIn != null &&
        dailyWillItSnow != null &&
        dailyChanceOfSnow != null &&
        avghumidity != null &&
        maxwindKph != null &&
        maxwindMph != null &&
        avgvisKm != null &&
        avgvisMiles != null &&
        uv != null

internal val AstroModel?.isValid: Boolean
    get() = this != null &&
        sunrise?.isNotEmpty() == true &&
        sunset?.isNotEmpty() == true &&
        moonrise?.isNotEmpty() == true &&
        moonset?.isNotEmpty() == true &&
        moonIllumination?.isNotEmpty() == true &&
        moonPhase?.isNotEmpty() == true

internal val HourModel.isValid: Boolean
    get() = isDay != null &&
        time?.isNotEmpty() == true &&
        timeEpoch != null &&
        tempC != null &&
        tempF != null &&
        feelslikeC != null &&
        feelslikeF != null &&
        windchillC != null &&
        windchillF != null &&
        windDir?.isNotEmpty() == true &&
        windDegree != null &&
        windKph != null &&
        windMph != null &&
        gustKph != null &&
        gustMph != null &&
        cloud != null &&
        humidity != null &&
        dewpointC != null &&
        dewpointF != null &&
        uv != null &&
        heatindexC != null &&
        heatindexF != null &&
        willItRain != null &&
        chanceOfRain != null &&
        precipMm != null &&
        precipIn != null &&
        condition.isValid &&
        willItSnow != null &&
        chanceOfSnow != null &&
        pressureMb != null &&
        pressureIn != null &&
        visKm != null &&
        visMiles != null
