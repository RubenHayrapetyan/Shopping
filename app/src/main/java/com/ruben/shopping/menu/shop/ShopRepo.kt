package com.ruben.shopping.menu.shop

import com.ruben.shopping.api.ApiClient
import com.ruben.shopping.api.SafeApiRequest
import com.ruben.shopping.appservice.PreferenceService

class ShopRepo(private val api: ApiClient, private val preferenceService: PreferenceService): SafeApiRequest() {

    internal suspend fun getShopProducts(recipeId: Int) = safeApiCall {
        api.checkRecipe(recipeId)
    }

    internal suspend fun addProductToRefrigerator(productName: String,
                                                  productWeightName: String,
                                                   productWeight: Float ) = safeApiCall {
        api.addProductToRefrigerator(productName, productWeightName, productWeight)
    }

    internal fun getRecipeId() = preferenceService.getRecipeId()
}