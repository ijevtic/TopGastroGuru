package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.models.User
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.presentation.view.states.AddUserState
import com.example.topgastroguru.presentation.view.states.CheckCredentialsState
import com.example.topgastroguru.presentation.view.states.UsersState

interface MealDetaildContract {

    interface ViewModel {
        val meal: LiveData<MealDetailed>

        fun fetchMealById(id:String, calValue: String)
        fun getMealById(id:String)
        fun saveMealToDB(meal: MealEntity)
    }
}