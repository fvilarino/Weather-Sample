package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.TimeProvider
import java.time.Instant
import javax.inject.Inject

internal class TimeProviderImpl @Inject constructor() : TimeProvider {
    override val epoch: Instant
        get() = Instant.now()
}
