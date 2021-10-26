package com.ruben.shopping.menu.recipe.page1

import android.util.Log
import com.ruben.shopping.api.ApiClient
import com.ruben.shopping.api.SafeApiRequest
import com.ruben.shopping.appservice.PreferenceService

class RecipeRepo(
        private val api: ApiClient,
        private val preferenceService: PreferenceService) : SafeApiRequest() {

    internal suspend fun getRecipes() = safeApiCall {
        api.getRecipes()
    }

    internal suspend fun checkRecipe(recipeId: Int) = safeApiCall {
        api.checkRecipe(recipeId)
    }

    internal fun setRecipeId(recipeId: Int){
        preferenceService.setRecipeId(recipeId)
    }
}