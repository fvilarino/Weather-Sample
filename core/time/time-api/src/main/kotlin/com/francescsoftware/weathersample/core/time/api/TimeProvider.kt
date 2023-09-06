package com.francescsoftware.weathersample.core.time.api

import java.time.Instant

/** Provides the current system time */
interface TimeProvider {
    /** Elapsed time since the Unix epoch */
    val epoch: Instant
}
