package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.models.responses.Meal
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.presentation.view.states.SingleMealState

interface MealEntityContract {

    interface ViewModel {
        val meal: LiveData<SingleMealState>
        val allMeals: LiveData<MealsState>

        fun getMealById(id: String)
        fun getAllMeals()
        fun deleteMealFromDB(id: String)
        fun editMealInDB(meal: MealEntity)
    }
}