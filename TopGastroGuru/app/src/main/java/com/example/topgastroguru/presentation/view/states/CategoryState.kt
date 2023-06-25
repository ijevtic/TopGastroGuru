package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.Category

sealed class CategoryState {
    object Empty: CategoryState()
    data class Selected(val category: Category): CategoryState()
    data class Error(val message: String): CategoryState()
}