package com.example.topgastroguru.data.models.responses

import com.example.topgastroguru.data.models.Parameter
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val categories: List<Category>,
): ParameterResponse
@JsonClass(generateAdapter = true)
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)