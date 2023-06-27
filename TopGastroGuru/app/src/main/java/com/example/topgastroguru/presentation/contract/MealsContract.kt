package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.presentation.view.states.MealsApiState
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.util.SortType

interface MealsContract {
    interface ViewModel {

        val initialRender: LiveData<Boolean>
        val loadedMealsData: LiveData<Boolean>
        val mealsState: LiveData<MealsApiState>
        val fullMealsState: LiveData<List<MealSimple>>
        val queryString: LiveData<String>
        val tagQuery: LiveData<String>

//        val queryChar: LiveData<Char>

        fun updateSearchQuery(query: String)
        fun fetchMealsByFilter(filter: String)
        fun fetchMealsByName(name: String)
        fun setFilter(parameter: Parameter?)
        fun setSort(sortType: SortType)
        fun setTag(tag: String)
        fun setInitialRender(value: Boolean)
        fun setLoadedMealsData(value: Boolean)

    }
}