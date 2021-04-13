package com.francescsoftware.weathersample.utils.time

import java.util.*

val Date.isToday: Boolean
    get() {
        val now = Calendar.getInstance()
        val date = Calendar.getInstance().apply { time = this@isToday }
        return now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            now.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
            now.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    }

val Date.isTomorrow: Boolean
    get() {
        val date = Calendar.getInstance().apply {
            time = this@isTomorrow
            add(Calendar.DAY_OF_MONTH, -1)
        }.time
        return date.isToday
    }

fun Millis.toDate(): Date = Date(value)
fun Seconds.toDate():Date = Date(milliseconds())

interface TimeFormatter {
    fun setToMidnight(date: Date): Date
    fun formatDayHour(date: Date): String
    fun formatDay(date: Date): String
    fun formatHour(date: Date): String
}
