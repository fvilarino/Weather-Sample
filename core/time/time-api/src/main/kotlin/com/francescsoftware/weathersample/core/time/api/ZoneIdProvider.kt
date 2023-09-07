package com.francescsoftware.weathersample.core.time.api

import java.time.ZoneId

/** Provides the local [ZoneId] for time conversions */
interface ZoneIdProvider {
    /** The local [ZoneId] */
    val zoneId: ZoneId
}
