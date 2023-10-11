package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.injection.AppScope
import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import com.squareup.anvil.annotations.ContributesBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val DayWithDayOfWeekFormat = "EEE MMMM, dd"
private const val HourFormat = "HH:mm"


@ContributesBinding(AppScope::class)
class TimeFormatterImpl @Inject constructor() : TimeFormatter {
    override fun formatDayWithDayOfWeek(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern(DayWithDayOfWeekFormat)
        return formatter.format(zonedDateTime)
    }

    override fun formatHour(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern(HourFormat)
        return formatter.format(zonedDateTime)
    }
}
