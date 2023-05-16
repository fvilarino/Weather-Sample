package com.francescsoftware.weathersample.core.type.either

import com.google.common.truth.Truth
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class EitherTest {

    @Test
    fun `successful Either returns true for isSuccess`() {
        val either: Either<Int> = Either.Success(0)
        Truth.assertThat(either.isSuccess).isTrue()
    }

    @Test
    fun `successful Either returns false for isFailure`() {
        val either: Either<Int> = Either.Success(0)
        Truth.assertThat(either.isFailure).isTrue()
    }

    @Test
    fun `failure Either returns false for isSuccess`() {
        val either: Either<Int> = Either.Failure(Throwable())
        Truth.assertThat(either.isSuccess).isFalse()
    }

    @Test
    fun `failure Either returns true for isFailure`() {
        val either: Either<Int> = Either.Failure(Throwable())
        Truth.assertThat(either.isFailure).isTrue()
    }

    @Test
    fun `successful Either runs onSuccess block on fold`() {
        val result = 42
        val either: Either<Int> = Either.Success(result)
        val callback: (Int) -> Unit = mockk(relaxed = true)
        either.fold(
            onSuccess = callback,
            onFailure = { }
        )
        verify(exactly = 1) { callback.invoke(result) }
    }

    @Test
    fun `failure Either runs onFailure block on fold`() {
        val throwable = Throwable()
        val either: Either<Int> = Either.Failure(throwable)
        val callback: (Throwable) -> Unit = mockk(relaxed = true)
        either.fold(
            onSuccess = { },
            onFailure = callback
        )
        verify(exactly = 1) { callback.invoke(throwable) }
    }

    @Test
    fun `success Either valueOrNull returns value`() {
        val result = 1
        val either: Either<Int> = Either.Success(result)
        Truth.assertThat(either.valueOrNull()).isEqualTo(result)
    }

    @Test
    fun `failure Either valueOrNull returns null`() {
        val either: Either<Int> = Either.Failure(Throwable())
        Truth.assertThat(either.valueOrNull()).isNull()
    }

    @Test
    fun `success Either throwableOrNull returns null`() {
        val result = 1
        val either: Either<Int> = Either.Success(result)
        Truth.assertThat(either.throwableOrNull()).isNull()
    }

    @Test
    fun `failure Either throwableOrNull returns throwable`() {
        val throwable = Throwable()
        val either: Either<Int> = Either.Failure(throwable)
        Truth.assertThat(either.throwableOrNull()).isEqualTo(throwable)
    }
}
