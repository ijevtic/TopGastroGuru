package com.example.topgastroguru.data.models.responses

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val meals: List<Category>
)
@JsonClass(generateAdapter = true)
data class Category(
    val strCategory: String,
)