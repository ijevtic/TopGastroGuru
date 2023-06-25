package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.Category

sealed class CategoriesState {
    object Loading: CategoriesState()
    data class Success(val categories: List<Category>): CategoriesState()
    data class Error(val message: String): CategoriesState()
}