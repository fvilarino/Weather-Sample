package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.TimeProvider
import kotlin.time.Duration

/** An implementation of [TimeProvider] to be used in tests */
class FakeTimeProvider : TimeProvider {
    private var currentEpoch: Duration = Duration.ZERO

    /** @{inheritDoc} */
    override val epoch: Duration
        get() = currentEpoch

    /** sets the epoch time for the fake provider */
    fun setCurrentEpoch(epoch: Duration) {
        currentEpoch = epoch
    }
}
