package com.example.topgastroguru.data.sources.remote.converters

import com.example.topgastroguru.data.models.MealDto
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.models.responses.Meal
import com.example.topgastroguru.data.models.responses.MealResponse
import java.util.Date

class MealDtoConverter {
    companion object {
        fun mapMealEntityToMealDto(mealEntity: MealEntity): MealDto {
            return MealDto(
                id = mealEntity.id,
                name = mealEntity.name,
                img = mealEntity.img,
                instructions = mealEntity.instructions,
                link = mealEntity.link,
                category = mealEntity.category,
                date = mealEntity.date,
                type = mealEntity.type
            )
        }

        fun mapListMealEntityToListMealDto(listMealEntity: List<MealEntity>): List<MealDto> {
            val listMealDto = mutableListOf<MealDto>()
            for (mealEntity in listMealEntity) {
                listMealDto.add(mapMealEntityToMealDto(mealEntity))
            }
            return listMealDto
        }

        fun mapMealResponseToListMealDto(listMealResponse: MealResponse): List<MealDto> {
            val listMealDto = mutableListOf<MealDto>()
            for (mealResponse in listMealResponse.meals) {
                listMealDto.add(mapMealResponseToMealDto(mealResponse))
            }
            return listMealDto
        }

        fun mapMealResponseToMealDto(mealResponse: Meal): MealDto {
            return MealDto(
                id = mealResponse.idMeal,
                name = mealResponse.strMeal?:"",
                img = mealResponse.strMealThumb?:"",
                instructions = mealResponse.strInstructions?:"",
                link = mealResponse.strSource?:"",
                category = mealResponse.strCategory?:"",
                date = Date(),
//                date = mealResponse.dateModified?:"",
                type = mealResponse.strArea?:""
            )
        }
    }
}