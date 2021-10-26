package com.ruben.shopping.appservice

import android.content.Context

class PreferenceServiceImpl(private val context: Context): PreferenceService {

    private val mainApp = "myApp"
    private val _recipeId = "recipeId"

    override fun setRecipeId(recipeId: Int) {
        context.getSharedPreferences(mainApp, Context.MODE_PRIVATE).edit()
                .putInt(_recipeId, recipeId).apply()
    }

    override fun getRecipeId(): Int {
        return context.getSharedPreferences(mainApp, Context.MODE_PRIVATE)
                .getInt(_recipeId, 0)
    }
}