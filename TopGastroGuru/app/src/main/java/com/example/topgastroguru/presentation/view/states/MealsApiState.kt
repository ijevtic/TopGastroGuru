package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.MealSimple

sealed class MealsApiState {
    object Loading: MealsApiState()
    data class Success(val meals: List<MealSimple>): MealsApiState()
    data class Error(val message: String): MealsApiState()
}