package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.presentation.view.states.CategoriesState

interface CategoriesContract {
    interface ViewModel {

        val categoriesState: LiveData<CategoriesState>

        fun fetchCategories()

    }
}