package com.example.topgastroguru.presentation.view.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.MealDetailed

class MeaDetailedlViewModel: ViewModel() {
    private val meal = MutableLiveData<MealDetailed>()

    fun getObligation(): LiveData<MealDetailed> {
        return meal
    }

    fun setObligation(mealD: MealDetailed) {
        meal.value = mealD
    }

}