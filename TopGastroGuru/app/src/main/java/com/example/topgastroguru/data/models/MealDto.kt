package com.example.topgastroguru.data.models

import java.util.Date

class MealDto (
    val id:String,
    val name: String,
    val img:String,
    val instructions:String,
    val link:String,
    val category: String,
    val date: Date,
    val type:String,
    val calValue: String
) {

}