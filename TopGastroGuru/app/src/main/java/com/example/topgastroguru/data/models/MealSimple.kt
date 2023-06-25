package com.example.topgastroguru.data.models

class MealSimple(
    val id:String,
    val name: String,
    val link: String,
){
    override fun toString(): String {
        return name
    }

}