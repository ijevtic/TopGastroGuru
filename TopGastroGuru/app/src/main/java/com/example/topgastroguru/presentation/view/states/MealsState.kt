package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.MealSimple

sealed class MealsState {
    object Loading: MealsState()
    data class Success(val meals: List<MealSimple>): MealsState()
    data class Error(val message: String): MealsState()
}