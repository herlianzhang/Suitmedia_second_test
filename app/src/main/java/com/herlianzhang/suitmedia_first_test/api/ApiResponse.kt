package com.herlianzhang.suitmedia_first_test.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

suspend fun <T> getResult(call: suspend () -> Response<T>) =
    flow<Result<T>> {
        try {
            emit(Result.Loading())
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) emit(Result.Success<T>(body))
            } else {
                emit(
                    Result.Error(
                        if (response.code() < 500) "Client Error" else "Server Error",
                        response.code()
                    )
                )
            }
        } catch (e: IOException) {
            emit(Result.Error("No Internet Connection"))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(Dispatchers.IO)
