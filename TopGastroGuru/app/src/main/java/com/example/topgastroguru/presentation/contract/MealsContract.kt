package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.presentation.view.states.MealsState

interface MealsContract {
    interface ViewModel {

        val mealsState: LiveData<MealsState>
        fun fetchMealsByFirstLetter(letter: Char)
        fun fetchMealsByFilter(filter: String)
        fun fetchMealsByName(name: String)

    }
}