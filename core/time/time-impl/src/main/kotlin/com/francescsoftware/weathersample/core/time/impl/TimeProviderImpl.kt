package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.TimeProvider
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class TimeProviderImpl @Inject constructor() : TimeProvider {
    override val epoch: Duration
        get() = System.currentTimeMillis().milliseconds
}
