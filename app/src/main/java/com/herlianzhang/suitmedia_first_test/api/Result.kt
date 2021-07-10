package com.herlianzhang.suitmedia_first_test.api

sealed class Result<out T> {
    data class Loading<T>(val value: Boolean = true) : Result<T>()
    data class Error<T>(val message: String?, val code: Int = 400) : Result<T>()
    data class Success<T>(val data: T?) : Result<T>()
}