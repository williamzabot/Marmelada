package br.com.digitalhouse.marmeladamovie.domain.utils


sealed class Result<out T : Any> {
    data class Success<out T : Any>(
        val data: T
    ) : Result<T>()

    data class Failure(val exception: Exception) : Result<Nothing>()
}

