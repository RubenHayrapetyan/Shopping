package com.ruben.shopping.models.shop

import com.squareup.moshi.Json

data class IngredientsModel(
    @field:Json(name = "data")
    val refProductsModel: List<IngredientsList>
)