package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.presentation.contract.MealEntityContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MealEntityViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealEntityContract.ViewModel {

    override val meal: MutableLiveData<MealEntity> = MutableLiveData()
    override val allMeals: MutableLiveData<List<MealEntity>> = MutableLiveData()

    private val subscriptions = CompositeDisposable()

    override fun getMealById(id: String) {
        val subscription = mealRepository
            .getMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meal.value = it
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMeals() {
        val subscription = mealRepository
            .getAllMeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allMeals.value = it
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteMealFromDB(meal: MealEntity) {
        TODO("Not yet implemented")
    }

    override fun editMealInDB(meal: MealEntity) {
        TODO("Not yet implemented")
    }
}