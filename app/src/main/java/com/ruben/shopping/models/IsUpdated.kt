package com.ruben.shopping.models

import com.squareup.moshi.Json

data class IsUpdated(
    @field:Json(name = "update") val isUpdated: String
)