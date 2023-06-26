package com.example.topgastroguru.data.models

class MealSimple(
    val id:String,
    val name: String?,
    val link: String?,
    val strCategory: String?,
    val strArea: String?,
    val strTags: String?,
    val ingredients: HashMap<String, String>
){
    override fun toString(): String {
        return name?:""
    }

    fun getIngredients(): String {
        var returnString = ""
        for(ingredient in ingredients) {
            returnString += "${ingredient.key} - ${ingredient.value}\n"
        }
        return returnString
    }

}