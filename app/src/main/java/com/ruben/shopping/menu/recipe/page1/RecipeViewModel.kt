package com.ruben.shopping.menu.recipe.page1

import com.ruben.shopping.app.BaseViewModel

class RecipeViewModel(private val repo: RecipeRepo): BaseViewModel() {

    internal fun getRecipes() = liveResponse {
        repo.getRecipes()
    }
    internal fun checkRecipe(recipeId: Int) = liveResponse {
        repo.checkRecipe(recipeId)
    }

    internal fun setRecipeId(recipeId: Int){
        repo.setRecipeId(recipeId)
    }
}