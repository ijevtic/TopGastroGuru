package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.entities.MealEntity

interface MealEntityContract {

    interface ViewModel {
        val meal: LiveData<MealEntity>
        val mealList: LiveData<List<MealEntity>>

        fun getMealById(id: String)
        fun getAllMeals()
        fun saveMealToDB(meal: MealEntity)
        fun deleteMealFromDB(meal: MealEntity)
        fun editMealInDB(meal: MealEntity)
    }
}