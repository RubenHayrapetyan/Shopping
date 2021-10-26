package com.ruben.shopping.models.refrigerator

import com.squareup.moshi.Json


data class RefrigeratorProductsModel(
        @field:Json(name = "data")
        val refProductsModel: List<RefrigeratorProductsList>
)