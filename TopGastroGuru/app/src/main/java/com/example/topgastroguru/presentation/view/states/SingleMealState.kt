package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.MealSimple

sealed class SingleMealState {
    object Loading: SingleMealState()
    data class Success(val meal: MealSimple): SingleMealState()
    data class Error(val message: String): SingleMealState()
}