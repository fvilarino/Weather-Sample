package com.francescsoftware.weathersample.core.time.api

import java.time.Instant

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
