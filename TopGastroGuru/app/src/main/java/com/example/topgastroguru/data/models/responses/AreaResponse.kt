package com.example.topgastroguru.data.models.responses


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class AreaResponse(
    val meals: List<Area>,
): ParameterResponse
@JsonClass(generateAdapter = true)
data class Area(
    val strArea: String,
)