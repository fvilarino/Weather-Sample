package com.francescsoftware.weathersample.core.time.api

import kotlin.time.Duration

/** Provides the current system time */
interface TimeProvider {
    /** Elapsed time since the Unix epoch */
    val epoch: Duration
}
