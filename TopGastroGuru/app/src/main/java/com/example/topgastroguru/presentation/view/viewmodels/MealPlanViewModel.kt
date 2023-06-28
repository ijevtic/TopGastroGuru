package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.sources.remote.converters.MealDtoConverter
import com.example.topgastroguru.presentation.contract.MealPlanContract
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.util.MealType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MealPlanViewModel(
    private val mealRepository: MealRepository,
): ViewModel(), MealPlanContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealTypeSelected: MutableLiveData<MealType> = MutableLiveData(MealType.ALLMEALS)
    override val allMeals: MutableLiveData<MealsState> = MutableLiveData()
    override val allMealsFiltered: MutableLiveData<MealsState> = MutableLiveData()
    override val localMeals: MutableLiveData<MealsState> = MutableLiveData()
    override val localMealsFiltered: MutableLiveData<MealsState> = MutableLiveData()

    override var queryString: MutableLiveData<String> = MutableLiveData("")

    private var queryChar: Char? = null

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        getAllMeals()
    }

    override fun updateQuery(query: String) {
        Timber.e("acac query: $query")
        var updatedChar = false
        if(query.isEmpty()) {
            queryChar = null
        } else {
            updatedChar = updateChar(query[0])
        }
        queryString.value = query
        if(updatedChar) {
            fetch()
        } else {
            applyFilters()
        }
    }

    override fun updateMealType(mealType: MealType) {
        mealTypeSelected.value = mealType
        //todo update view
    }

    private fun updateChar(c: Char): Boolean {
        if(queryChar != c) {
            queryChar = c
            return true
        }
        return false
    }

    private fun fetch() {
        Timber.e("acac fetch")
        fetchMealsByFirstLetter()
        applyFiltersToLocalMeals()
    }


    private fun fetchMealsByFirstLetter() {
        if(queryChar == null) {
            allMeals.value = MealsState.Success(listOf())
            applyFilters()
            return
        }
        val subscription = mealRepository
            .fetchMealsByFirstLetter(queryChar!!)
            .map<MealsState> {
                MealsState.Success(
                    MealDtoConverter.mapMealResponseToListMealDto(
                        it
                    )
                )
            }
            .startWith(MealsState.Loading)
            .onErrorReturn { MealsState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString())
                when (it) {
                    is MealsState.Success -> {
                        Timber.e("acac fetched " + it.meals.size)
                        allMeals.value = MealsState.Success(it.meals)
                        applyFilters()
                    }

                    is MealsState.Loading -> {
                        allMeals.value = MealsState.Loading
                    }

                    is MealsState.Error -> {
                        allMeals.value = MealsState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }

    private fun applyFilters() {
        applyFiltersToAllMeals()
        applyFiltersToLocalMeals()
    }

    private fun applyFiltersToAllMeals() {
        Timber.e("acac apply filters online" + (allMeals.value is MealsState.Success))
        if (allMeals.value is MealsState.Success) {
            Timber.e("acac" + (allMeals.value as MealsState.Success).meals.size)
            val meals = (allMeals.value as MealsState.Success).meals
            if (queryChar == null) {
                allMeals.value = MealsState.Success(listOf())
                allMealsFiltered.value = MealsState.Success(listOf())
                return
            }
            val filteredMeals = meals.filter { meal ->
                meal.name.startsWith(queryString.value!!, true)
            }
            allMealsFiltered.value = MealsState.Success(filteredMeals)
            Timber.e("acac apply filters online ok" + filteredMeals.size)
        }
        else {
            allMealsFiltered.value = MealsState.Success(listOf())
        }
    }

    private fun applyFiltersToLocalMeals() {
        Timber.e("acac apply filters local")
        if (localMeals.value is MealsState.Success) {
            val meals = (localMeals.value as MealsState.Success).meals
            if (queryString.value!!.isEmpty()) {
                localMealsFiltered.value = MealsState.Success(meals)
                return
            }
            val filteredMeals = meals.filter { meal ->
                meal.name.startsWith(queryString.value!!, true)
            }
            localMealsFiltered.value = MealsState.Success(filteredMeals)
        } else {
            localMealsFiltered.value = MealsState.Success(listOf())
        }
    }

    private fun getAllMeals() {
        val subscription = mealRepository
            .getAllMeals()
            .map<MealsState> { MealsState.Success(MealDtoConverter.mapListMealEntityToListMealDto(it)) }
            .startWith(MealsState.Loading)
            .onErrorReturn { MealsState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString());
                when (it) {
                    is MealsState.Success -> {
                        localMeals.value = MealsState.Success(it.meals)
                        applyFilters()
                    }
                    is MealsState.Loading -> {
                        localMeals.value = MealsState.Loading
                    }
                    is MealsState.Error -> {
                        localMeals.value = MealsState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }


}