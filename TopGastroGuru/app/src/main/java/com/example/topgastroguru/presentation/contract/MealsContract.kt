package com.example.topgastroguru.presentation.contract

interface MealsContract {
    interface ViewModel {
        fun fetchAllMeals()
        fun fetchMealsByFilter(filter: String)
        fun fetchMealsByName(name: String)

    }
}