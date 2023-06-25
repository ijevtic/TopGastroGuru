package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.sources.remote.converters.MealSimpleConverter
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.states.MealsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


class AllMealsViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealsContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val fullMealsState: MutableLiveData<List<MealSimple>> = MutableLiveData()
//    private val filterUpdate: MutableLiveData<Boolean> = MutableLiveData()
    private var queryChar: Char? = null
    private var queryString: String? = null

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
//        filterUpdate.observeForever { state ->
//            if(!state)
//                return@observeForever
//
//            filterMeals()
//            filterUpdate.value = false
//        }
    }


    private fun fetchMealsByFirstLetter() {
        if(queryChar == null) {
            fullMealsState.value = listOf()
            mealsState.value = MealsState.Success(listOf())
            return
        }
        val subscription = mealRepository
            .fetchMealsByFirstLetter(queryChar!!)
            .map<MealsState> { MealsState.Success(MealSimpleConverter.mapMealResponseToMealSimple(it)) }
            .startWith(MealsState.Loading)
            .onErrorReturn { MealsState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString());
                when (it) {
                    is MealsState.Success -> {
                        fullMealsState.value = it.meals
                        applyFilters()
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

    private fun applyFilters() {
        if(fullMealsState.value == null) {
            mealsState.value = MealsState.Success(listOf())
            return
        }
        val filteredMeals = mutableListOf<MealSimple>()
        if(queryString != null) {
            for (meal in fullMealsState.value!!) {
                if (meal.name!!.startsWith(queryString!!, true)) {
                    filteredMeals.add(meal)
                }
            }
        } else {
            filteredMeals.addAll(fullMealsState.value!!)
        }
        mealsState.value = MealsState.Success(filteredMeals)
    }

    override fun updateSearchQuery(query: String) {

        var changedString = false

        if (query != queryString) {
            queryString = query
            changedString = true
        }

        val updatedFirstLetter = setQueryChar(query)

        if(updatedFirstLetter)
            fetchMealsByFirstLetter()
        else if(changedString)
            applyFilters()
    }

    private fun setQueryChar(query: String): Boolean {
        if(query.isEmpty()) {
            if (queryChar == null)
                return false
            queryChar = null
            return true
        }
        if(queryChar == query[0])
            return false

        queryChar = query[0]
        return true
    }

    override fun fetchMealsByFilter(filter: String) {
        TODO("Not yet implemented")
    }

    override fun fetchMealsByName(name: String) {
        TODO("Not yet implemented")
    }
}