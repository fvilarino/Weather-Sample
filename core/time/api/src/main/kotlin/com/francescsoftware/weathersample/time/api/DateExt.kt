package com.francescsoftware.weathersample.time.api

import java.util.Calendar
import java.util.Date

/**
 * Checks if this [Date] is today
 */
val Date.isToday: Boolean
    get() {
        val now = Calendar.getInstance()
        val date = Calendar.getInstance().apply { time = this@isToday }
        return now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            now.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
            now.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    }

/**
 * Checks if this [Date] is tomorrow
 */
val Date.isTomorrow: Boolean
    get() {
        val date = Calendar.getInstance().apply {
            time = this@isTomorrow
            add(Calendar.DAY_OF_MONTH, -1)
        }.time
        return date.isToday
    }
