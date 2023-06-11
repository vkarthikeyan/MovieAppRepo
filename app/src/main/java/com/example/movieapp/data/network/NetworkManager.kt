package com.example.movieapp.data.network

import com.example.movieapp.data.model.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(
        val exception: Exception, var errorResponse: ErrorResponse? = null
    ) : Result<Nothing>()
}

suspend fun <T : Any> apiCall(
    call: suspend () -> Result<T>
): Result<T> = try {
    call.invoke()

} catch (e: HttpException) {
    Result.Error(
        IOException(
            e.message,
            e
        )
    )
} catch (e: java.lang.Exception) {
    Result.Error(
        IOException(
            e.message,
            e
        )
    )
} catch (e: Throwable) {
    Result.Error(
        IOException(
            e.message,
            e
        )
    )
}
