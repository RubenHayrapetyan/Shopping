package com.ruben.shopping.models.refrigerator

import com.squareup.moshi.Json

data class RefrigeratorProductsList(

    @field:Json(name = "id") val ingredientId: Int?,

    @field:Json(name = "all") var ingredientCount: Float?,

    @field:Json(name = "weight") val ingredientWeight: String?,

    @field:Json(name = "name") val ingredientTitle: String?
)
