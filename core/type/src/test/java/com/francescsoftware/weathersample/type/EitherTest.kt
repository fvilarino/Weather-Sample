package com.francescsoftware.weathersample.type

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EitherTest {

    @Test
    fun `successful Either returns true for isSuccess`() {
        val either: Either<Int> = Either.Success(0)

        Assertions.assertTrue(either.isSuccess)
    }

    @Test
    fun `successful Either returns false for isFailure`() {
        val either: Either<Int> = Either.Success(0)

        Assertions.assertFalse(either.isFailure)
    }

    @Test
    fun `failure Either returns false for isSuccess`() {
        val either: Either<Int> = Either.Failure(Throwable())

        Assertions.assertFalse(either.isSuccess)
    }

    @Test
    fun `failure Either returns true for isFailure`() {
        val either: Either<Int> = Either.Failure(Throwable())

        Assertions.assertTrue(either.isFailure)
    }

    @Test
    fun `successful Either runs onSuccess block on fold`() {
        val result = 0
        val either: Either<Int> = Either.Success(result)
        var actual: Int? = null
        either.fold(
            onSuccess = { value -> actual = value },
            onFailure = { }
        )
        Assertions.assertEquals(result, actual)
    }

    @Test
    fun `failure Either runs onFailure block on fold`() {
        val either: Either<Int> = Either.Failure(Throwable())
        var actual: Int? = 0
        either.fold(
            onSuccess = { value -> actual = value },
            onFailure = { actual = null }
        )
        Assertions.assertEquals(null, actual)
    }

    @Test
    fun `success Either valueOrNull returns value`() {
        val result = 1
        val either: Either<Int> = Either.Success(result)

        Assertions.assertEquals(result, either.valueOrNull())
    }

    @Test
    fun `failure Either valueOrNull returns null`() {
        val either: Either<Int> = Either.Failure(Throwable())

        Assertions.assertNull(either.valueOrNull())
    }

    @Test
    fun `success Either throwableOrNull returns null`() {
        val result = 1
        val either: Either<Int> = Either.Success(result)

        Assertions.assertNull(either.throwableOrNull())
    }

    @Test
    fun `failure Either throwableOrNull returns throwable`() {
        val throwable = Throwable()
        val either: Either<Int> = Either.Failure(throwable)

        Assertions.assertEquals(throwable, either.throwableOrNull())
    }
}
