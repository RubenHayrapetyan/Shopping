package com.ruben.shopping.menu.refrigerator

import com.ruben.shopping.app.BaseViewModel
import okhttp3.RequestBody

class RefrigeratorViewModel(private val repo: RefrigeratorRepo): BaseViewModel() {

    internal fun getProducts() = liveResponse {
        repo.getRefrigeratorProducts()
    }

    internal fun updateRefrigerator(jsonArray: RequestBody) = liveResponse {
        repo.updateRefrigerator(jsonArray)
    }

    internal fun deleteAllProducts() = liveResponse {
        repo.deleteAllProducts()
    }

    internal fun deleteSelectedIngredients(jsonArray: RequestBody) = liveResponse {
        repo.deleteSelectedIngredients(jsonArray)
    }
}