package com.ruben.shopping.interfaces

import com.ruben.shopping.models.recipe.Recipe

interface RecipeClickListener {

    fun onRecipeClick(recipe: Recipe)
    fun onToUseBtnClick(recipe: Recipe)
}