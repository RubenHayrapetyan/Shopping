package com.ruben.shopping.api

import retrofit2.Response

abstract class SafeApiRequest {

    protected inline fun <T> safeApiCall(call: () -> Response<T>): ResponseResult<T> {
        return try {
            val response = call.invoke()
            return if (response.isSuccessful){
                ResponseResult.Success(response.body()!!)
            }else{
                val error = response.errorBody()?.string()
                ResponseResult.Failure("$error")
            }
        } catch (e: Exception) {
            ResponseResult.Failure("${e.message}")
        }
    }
}