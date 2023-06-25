package com.example.topgastroguru.presentation.view.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.models.Resource
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.mapper.MealMapper
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.presentation.view.states.UsersState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class AllMealsViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealsContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()

    override fun fetchMealsByFirstLetter(letter: Char) {
        val subscription = mealRepository
            .fetchMealsByFirstLetter(letter)
            .map<MealsState> { MealsState.Success(MealMapper.mapMealResponseToMealSimple(it)) }
            .startWith(MealsState.Loading)
            .onErrorReturn { MealsState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString());
                when (it) {
                    is MealsState.Success -> {
                        mealsState.value = MealsState.Success(it.meals)
//                        Toast.makeText(getApplicationContext(), "Success: ${it.meals.size}", Toast.LENGTH_SHORT).show()
                    }
                    is MealsState.Loading -> {
                        mealsState.value = MealsState.Loading
                    }
                    is MealsState.Error -> {
                        mealsState.value = MealsState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }

    override fun fetchMealsByFilter(filter: String) {
        TODO("Not yet implemented")
    }

    override fun fetchMealsByName(name: String) {
        TODO("Not yet implemented")
    }
}