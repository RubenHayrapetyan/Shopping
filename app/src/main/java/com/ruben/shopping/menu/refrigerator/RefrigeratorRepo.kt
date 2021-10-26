package com.ruben.shopping.menu.refrigerator

import com.ruben.shopping.api.ApiClient
import com.ruben.shopping.api.SafeApiRequest
import okhttp3.RequestBody

class RefrigeratorRepo(private val api: ApiClient) : SafeApiRequest() {

    internal suspend fun getRefrigeratorProducts() = safeApiCall {
        api.getRefrigeratorIngredients()
    }

    internal suspend fun updateRefrigerator(jsonArray: RequestBody) = safeApiCall {
        api.updateRefrigerator(jsonArray)
    }

    internal suspend fun deleteAllProducts() = safeApiCall {
        api.deleteSelectedProducts()
    }

    internal suspend fun deleteSelectedIngredients(jsonArray: RequestBody) = safeApiCall {
        api.deleteSelectedProducts(jsonArray)
    }
}