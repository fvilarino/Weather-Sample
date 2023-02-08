package com.francescsoftware.weathersample.type

sealed interface Either<out T> {
    class Success<T>(val value: T) : Either<T>
    class Failure(val error: Throwable) : Either<Nothing>
}

inline fun <R, T> Either<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R,
): R = when (this) {
    is Either.Success -> onSuccess(value)
    is Either.Failure -> onFailure(error)
}

val <T> Either<T>.isSuccess: Boolean
    get() = this is Either.Success

val <T> Either<T>.isFailure: Boolean
    get() = this is Either.Failure

fun <T> Either<T>.valueOrNull(): T? = (this as? Either.Success)?.value

fun <T> Either<T>.throwableOrNull(): Throwable? = (this as? Either.Failure)?.error
