package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.MealDto

sealed class MealsState {
    object Loading: MealsState()
    data class Success(val meals: List<MealDto>): MealsState()
    data class Error(val message: String): MealsState()
}