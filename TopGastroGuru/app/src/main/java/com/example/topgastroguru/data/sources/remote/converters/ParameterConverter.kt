package com.example.topgastroguru.data.sources.remote.converters

import com.example.topgastroguru.data.models.Area
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.data.models.Ingredient
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.data.models.responses.AreaResponse
import com.example.topgastroguru.data.models.responses.CategoryResponse
import com.example.topgastroguru.data.models.responses.IngredientResponse
import com.example.topgastroguru.data.models.responses.ParameterResponse


class ParameterConverter {
    companion object {
        fun mapParameterResponseToParameter(parameterResponse: ParameterResponse): List<Parameter> {
            return when (parameterResponse) {
                is CategoryResponse -> mapCategoryResponseToCategory(parameterResponse)
                is AreaResponse -> mapAreaResponseToArea(parameterResponse)
                is IngredientResponse -> mapIngredientResponseToIngredient(parameterResponse)
                else -> throw IllegalArgumentException("Unknown response type")
            }
        }

        private fun mapCategoryResponseToCategory(categoryResponse: CategoryResponse): List<Category> {
            val returnList: MutableList<Category> = mutableListOf()
            categoryResponse.categories.forEach {
                returnList.add(
                    Category(
                        idCategory = it.idCategory,
                        strCategory = it.strCategory,
                        strCategoryThumb = it.strCategoryThumb,
                        strCategoryDescription = it.strCategoryDescription
                    )
                )
            }
            return returnList
        }

        private fun mapAreaResponseToArea(areaResponse: AreaResponse): List<Area> {
            val returnList: MutableList<Area> = mutableListOf()
            areaResponse.meals.forEach {
                returnList.add(
                    Area(
                        strArea = it.strArea
                    )
                )
            }
            return returnList
        }

        private fun mapIngredientResponseToIngredient(ingredientResponse: IngredientResponse): List<Ingredient> {
            val returnList: MutableList<Ingredient> = mutableListOf()
            ingredientResponse.meals.forEach {
                returnList.add(
                    Ingredient(
                        idIngredient = it.idIngredient,
                        strIngredient = it.strIngredient,
                        strDescription = it.strDescription
                    )
                )
            }
            return returnList
        }
    }
}