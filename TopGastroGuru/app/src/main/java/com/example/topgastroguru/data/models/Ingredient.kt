package com.example.topgastroguru.data.models

import com.example.topgastroguru.util.ParameterType

class Ingredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    override val type: ParameterType = ParameterType.INGREDIENT
): Parameter{
    override fun toString(): String {
        return strIngredient
    }

    override fun areItemsTheSame(newItem: Parameter): Boolean {
        return newItem is Ingredient && idIngredient == newItem.idIngredient
    }

    override fun areContentsTheSame(newItem: Parameter): Boolean {
        return newItem is Ingredient && strIngredient == newItem.strIngredient
    }
}