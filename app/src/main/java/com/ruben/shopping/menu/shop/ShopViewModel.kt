package com.ruben.shopping.menu.shop

import com.ruben.shopping.app.BaseViewModel

class ShopViewModel(private val repo: ShopRepo) : BaseViewModel() {

    internal fun getProducts(recipeId: Int) = liveResponse {
        repo.getShopProducts(recipeId)
    }

    internal fun addProductToRefrigerator(productName: String,
                                          productWeightName: String,
                                          productWeight: Float) = liveResponse {
        repo.addProductToRefrigerator(productName, productWeightName, productWeight)
    }

    internal fun getRecipeId(): Int = repo.getRecipeId()
}