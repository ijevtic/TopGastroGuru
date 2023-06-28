package com.example.topgastroguru.data.sources.remote.converters

import com.example.topgastroguru.data.models.MealDto
import com.example.topgastroguru.data.models.entities.MealEntity

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

        fun mapMealDtoToMealEntity(mealDto: MealDto): MealEntity {
            return MealEntity(
                id = mealDto.id,
                name = mealDto.name,
                img = mealDto.img,
                instructions = mealDto.instructions,
                link = mealDto.link,
                category = mealDto.category,
                date = mealDto.date,
                type = mealDto.type
            )
        }

        fun mapListMealEntityToListMealDto(listMealEntity: List<MealEntity>): List<MealDto> {
            val listMealDto = mutableListOf<MealDto>()
            for (mealEntity in listMealEntity) {
                listMealDto.add(mapMealEntityToMealDto(mealEntity))
            }
            return listMealDto
        }
    }
}