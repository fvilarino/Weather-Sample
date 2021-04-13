package com.francescsoftware.weathersample.utils.time

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val DAY_HOUR_FORMAT = "MMMM, dd HH:mm"
private const val DAY_WITH_DAY_OF_WEEK_FORMAT = "EEE MMMM, dd"
private const val DAY_FORMAT = "MMMM, dd"
private const val HOUR_FORMAT = "HH:mm"

class TimeFormatterImpl @Inject constructor() : TimeFormatter {
    override fun setToMidnight(date: Date): Date = Calendar.getInstance().let { calendar ->
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.time
    }

    override fun formatDayHour(date: Date): String {
        val formatter = SimpleDateFormat(DAY_HOUR_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatDayWithDayOfWeek(date: Date): String {
        val formatter = SimpleDateFormat(DAY_WITH_DAY_OF_WEEK_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatDay(date: Date): String {
        val formatter = SimpleDateFormat(DAY_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    override fun formatHour(date: Date): String {
        val formatter = SimpleDateFormat(HOUR_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
}
