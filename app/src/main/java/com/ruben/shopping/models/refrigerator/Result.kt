package com.ruben.shopping.models.refrigerator

import com.squareup.moshi.Json

data class Result(
        @field:Json(name = "result")
        val result: String?
)
