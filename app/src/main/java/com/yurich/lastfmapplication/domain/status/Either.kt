package com.yurich.lastfmapplication.domain.status

sealed class Either<T> {
    class Result<T>(val result: T) : Either<T>()
    class Error<T> : Either<T>()
}