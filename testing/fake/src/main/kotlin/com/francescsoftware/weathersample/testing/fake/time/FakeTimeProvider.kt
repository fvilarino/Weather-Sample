package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.TimeProvider
import java.time.Instant

/** An implementation of [TimeProvider] to be used in tests */
class FakeTimeProvider : TimeProvider {
    private var currentEpoch: Instant = Instant.EPOCH

    /** @{inheritDoc} */
    override val epoch: Instant
        get() = currentEpoch

    /** sets the epoch time for the fake provider */
    fun setCurrentEpoch(epoch: Instant) {
        currentEpoch = epoch
    }
}
