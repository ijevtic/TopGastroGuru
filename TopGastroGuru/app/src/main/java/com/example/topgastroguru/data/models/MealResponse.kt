package com.example.topgastroguru.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResponse(
    val id: String,
    val title: String
)