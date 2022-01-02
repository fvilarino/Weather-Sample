package com.francescsoftware.weathersample.time.api

import java.util.Date

interface TimeFormatter {
    fun setToMidnight(date: Date): Date
    fun formatDayHour(date: Date): String
    fun formatDayWithDayOfWeek(date: Date): String
    fun formatDay(date: Date): String
    fun formatHour(date: Date): String
}
