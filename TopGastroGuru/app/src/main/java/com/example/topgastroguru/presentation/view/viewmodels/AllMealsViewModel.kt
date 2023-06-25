package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.repositories.MealRepository


class AllMealsViewModel(
    private val mealRepository: MealRepository
): ViewModel(), AllMealsContract.ViewModel {
}