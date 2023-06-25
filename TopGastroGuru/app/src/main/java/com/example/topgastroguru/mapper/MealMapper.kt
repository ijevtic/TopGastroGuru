package com.example.topgastroguru.mapper

import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.models.responses.Meal
import com.example.topgastroguru.data.models.responses.MealResponse

class MealMapper {
    companion object {
        fun mapMealResponseToMealSimple(mealResponse: MealResponse): List<MealSimple> {
            val returnList: MutableList<MealSimple> = mutableListOf()
            for(meal: Meal in mealResponse.meals) {
                returnList += MealSimple(
                    id = meal.idMeal,
                    name = meal.strMeal,
                    link = meal.strMealThumb
                )
            }
            return returnList
        }
    }
}