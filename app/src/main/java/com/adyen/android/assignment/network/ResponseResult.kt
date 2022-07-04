package com.adyen.android.assignment.network

import com.squareup.moshi.JsonClass

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure(val error: ErrorResponse) : ResponseResult<Nothing>()
    object NetworkError : ResponseResult<Nothing>()
}

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val statusCode: Int = 0,
    val errorCode: Int? = 0,
    val message: String?
)

suspend fun <T> callApi(apiCall: suspend () -> T): ResponseResult<T> {
    return try {
        val result = apiCall.invoke()
        ResponseResult.Success(result)
    } catch (throwable: Throwable) {
        ResponseResult.Failure(createUnexpectedError(throwable))
    }
}

fun createUnexpectedError(throwable: Throwable) =
    ErrorResponse(-1, -1, throwable.message)