package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.presentation.view.states.CategoriesState
import com.example.topgastroguru.presentation.view.states.CategoryState

interface CategoriesContract {
    interface ViewModel {

        val categoriesState: LiveData<CategoriesState>
        val selectedCategoryState: LiveData<CategoryState>
        fun fetchCategories()

    }
}