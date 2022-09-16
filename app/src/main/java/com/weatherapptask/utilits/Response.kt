package com.weatherapptask.utilits

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weatherapptask.network.responseError.ResponseError

data class Response<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val throwable: Throwable?,
    val responseCode: Int = 0
) {
    var isSuccess: Boolean = false
        get() {
            return status == Status.SUCCESS
        }
        private set

    companion object {
        private fun <T> success(data: T): Response<T> =
            Response(status = Status.SUCCESS, data = data, message = null, throwable = null)

        fun <T> error(
            error: Throwable?,
            message: String?,
            responseCode: Int = 0
        ): Response<T> =
            Response(
                status = Status.ERROR,
                data = null,
                message = message,
                throwable = error,
                responseCode = responseCode
            )


        suspend fun <T> handle(
            getResponse: suspend () -> retrofit2.Response<T>,
            onSuccess: suspend (T) -> Response<T> = { success(it) },
            onFailure: suspend (Response<T>) -> Response<T> = { it }
        ): Response<T> {
            return try {
                val response = getResponse()
                if (response.isSuccessful && response.body() != null)
                    onSuccess(response.body()!!)
                else {
                    val gson = Gson()
                    val type = object : TypeToken<ResponseError>() {}.type
                    val errorResponse: ResponseError? =
                        gson.fromJson(response.errorBody()?.charStream(), type)
                    onFailure(error(null, errorResponse?.error?.message))
                }
            } catch (t: Throwable) {
                onFailure(error(t, null))
            }
        }
    }
}