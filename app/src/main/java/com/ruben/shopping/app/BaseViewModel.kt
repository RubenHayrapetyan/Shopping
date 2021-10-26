package com.ruben.shopping.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ruben.shopping.api.ResponseResult
import kotlinx.coroutines.Dispatchers

open class BaseViewModel: ViewModel() {

    protected inline fun <T> liveResponse(crossinline body: suspend () -> ResponseResult<T>) =
            liveData(Dispatchers.IO) {
                emit(ResponseResult.Loading)
                val result = body()
                emit(result)
                emit(ResponseResult.Complete)
            }
}