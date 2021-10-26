package com.ruben.shopping.models.recipe

import com.squareup.moshi.Json

data class RecipeModel(

        @field:Json(name = "data") val data: List<Recipe>?
)