package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealDto
import com.example.topgastroguru.util.Weekday

interface PlanOverViewContract {
    interface ViewModel {

        val mondayState: LiveData<List<MealDto>>
        val tuesdayState: LiveData<List<MealDto>>
        val fridayState: LiveData<List<MealDto>>

        fun addMealToDay(meal: MealDto, day: Weekday)

        fun removeMealFromDay(mealId: String, day: Weekday)

    }
}