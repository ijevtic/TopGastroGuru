package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.MealSimple

sealed class SingleMealApiState {
    object Loading: SingleMealApiState()
    data class Success(val meal: MealSimple): SingleMealApiState()
    data class Error(val message: String): SingleMealApiState()
}