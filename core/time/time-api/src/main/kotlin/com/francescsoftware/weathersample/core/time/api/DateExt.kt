package com.francescsoftware.weathersample.core.time.api

import java.time.ZonedDateTime

/** Checks if this [ZonedDateTime] is today */
val ZonedDateTime.isToday: Boolean
    get() {
        val now = ZonedDateTime.now()
        return year == now.year && dayOfYear == now.dayOfYear
    }

/** Checks if this [ZonedDateTime] is tomorrow */
val ZonedDateTime.isTomorrow: Boolean
    get() {
        val tomorrow = ZonedDateTime.now().plusDays(1)
        return year == tomorrow.year && dayOfYear == tomorrow.dayOfYear
    }
