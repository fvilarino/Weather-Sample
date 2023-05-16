package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val DayHourFormat = "MMMM, dd HH:mm"
private const val DayWithDayOfWeekFormat = "EEE MMMM, dd"
private const val DayFormat = "MMMM, dd"
private const val HourFormat = "HH:mm"
internal class TimeFormatterImpl @Inject constructor() : TimeFormatter {
    override fun setToMidnight(date: Date): Date = Calendar.getInstance().let { calendar ->
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.time
    }

    override fun formatDayHour(date: Date): String {
        val formatter = SimpleDateFormat(DayHourFormat, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatDayWithDayOfWeek(date: Date): String {
        val formatter = SimpleDateFormat(DayWithDayOfWeekFormat, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatDay(date: Date): String {
        val formatter = SimpleDateFormat(DayFormat, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatHour(date: Date): String {
        val formatter = SimpleDateFormat(HourFormat, Locale.getDefault())
        return formatter.format(date)
    }
}
