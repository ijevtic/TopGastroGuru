package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.presentation.view.states.MealsState

interface MealsContract {
    interface ViewModel {

        val mealsState: LiveData<MealsState>
        val fullMealsState: LiveData<List<MealSimple>>

//        val queryChar: LiveData<Char>

        fun updateSearchQuery(query: String)
        fun fetchMealsByFilter(filter: String)
        fun fetchMealsByName(name: String)

    }
}