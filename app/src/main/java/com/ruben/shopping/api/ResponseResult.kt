package com.ruben.shopping.api

sealed class ResponseResult<out H> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure(val errorMessage: String?) : ResponseResult<Nothing>()
    object Loading : ResponseResult<Nothing>()
    object Complete : ResponseResult<Nothing>()
}