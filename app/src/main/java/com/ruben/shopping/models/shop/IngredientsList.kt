package com.ruben.shopping.models.shop

import com.squareup.moshi.Json

data class IngredientsList(

        val ingredientId: Int?,

        @field:Json(name = "all") val ingredientCount: Float?,

        @field:Json(name = "weight") val ingredientWeight: String?,

        @field:Json(name = "name") val ingredientTitle: String?
)