package com.ruben.shopping.appservice

interface PreferenceService {

    fun setRecipeId(recipeId: Int)
    fun getRecipeId(): Int
}