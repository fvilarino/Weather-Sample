package com.francescsoftware.weathersample.type

sealed class Result<out T> {
    class Success<T>(val value: T) : Result<T>()
    class Failure(val error: Throwable) : Result<Nothing>()
}

inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R,
): R = when (this) {
    is Result.Success -> onSuccess(value)
    is Result.Failure -> onFailure(error)
}

val <T> Result<T>.isSuccess: Boolean
    get() = when (this) {
        is Result.Success -> true
        is Result.Failure -> false
    }

val <T> Result<T>.isFailure: Boolean
    get() = when (this) {
        is Result.Success -> false
        is Result.Failure -> true
    }

fun <T> Result<T>.getOrNull(): T? =
    when (this) {
        is Result.Success -> value
        is Result.Failure -> null
    }

fun <T> Result<T>.throwableOrNull(): Throwable? =
    when (this) {
        is Result.Success -> null
        is Result.Failure -> error
    }
