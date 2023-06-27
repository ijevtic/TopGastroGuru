package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.presentation.view.states.MealsApiState
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.util.MealType
import com.example.topgastroguru.util.SortType

interface MealPlanContract {
    interface ViewModel {

        val queryString: LiveData<String>
//        val queryChar: LiveData<Char>
        val mealTypeSelected: LiveData<MealType>
        val allMeals: LiveData<MealsState>
        val allMealsFiltered: LiveData<MealsState>
        val localMeals: LiveData<MealsState>
        val localMealsFiltered: LiveData<MealsState>

        fun updateQuery(query: String)
        fun updateMealType(mealType: MealType)
    }
}