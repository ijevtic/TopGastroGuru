package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.Area
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.data.models.Ingredient
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.sources.remote.converters.MealSimpleConverter
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.states.MealsApiState
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.util.SortType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


class AllMealsViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealsContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealsApiState> = MutableLiveData()
    override val fullMealsState: MutableLiveData<List<MealSimple>> = MutableLiveData()
    private var tagQuery: String? = null
    private var sortParameter: SortType? = null
    private var queryChar: Char? = null
    private var queryString: String? = null
    private var parameter: Parameter? = null
    private var brojac = 0

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
    }


    private fun fetchMeals() {
        Timber.e("fetchMeals " + queryChar + " " + queryString + " " + parameter)
        if(queryChar == null) {
            Timber.e("uso")
            fetchMealsByParameter()
        }
        else {
            Timber.e("uso2")
            fetchMealsByFirstLetter()
        }
    }

    private fun fetchMealsByFirstLetter() {
        val subscription = mealRepository
            .fetchMealsByFirstLetter(queryChar!!)
            .map<MealsApiState> { MealsApiState.Success(MealSimpleConverter.mapMealResponseToMealSimple(it)) }
            .startWith(MealsApiState.Loading)
            .onErrorReturn { MealsApiState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString())
                when (it) {
                    is MealsApiState.Success -> {
                        fullMealsState.value = it.meals
                        brojac++
                        fetchCalories(brojac)
                        applyFilters()
                    }
                    is MealsApiState.Loading -> {
                        mealsState.value = MealsApiState.Loading
                    }
                    is MealsApiState.Error -> {
                        mealsState.value = MealsApiState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }

    private fun fetchCalories(initBrojac: Int) {
        ///
//        fullMealsStateCalories = listOf()

//        if(initBrojac == brojac) {
//            fullMealsState.value = //
//            applyFilters()
//        }
    }

    private fun fetchMealsByParameter() {
        if(parameter == null) {
            fullMealsState.value = listOf()
            mealsState.value = MealsApiState.Success(listOf())
            return
        }
        val subscription = mealRepository
            .fetchMealsByParameter(parameter!!)
            .map<MealsApiState> { MealsApiState.Success(MealSimpleConverter.mapMealResponseToMealSimple(it)) }
            .startWith(MealsApiState.Loading)
            .onErrorReturn { MealsApiState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response meals" + it.toString());
                when (it) {
                    is MealsApiState.Success -> {
                        fullMealsState.value = it.meals
                        applyFilters()
                    }
                    is MealsApiState.Loading -> {
                        mealsState.value = MealsApiState.Loading
                    }
                    is MealsApiState.Error -> {
                        mealsState.value = MealsApiState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }

    // just filters without fetching
    private fun applyFilters() {
        if(fullMealsState.value == null) {
            mealsState.value = MealsApiState.Success(listOf())
            return
        }
        val filteredMeals = mutableListOf<MealSimple>()
        if(queryChar != null) {
            for (meal in fullMealsState.value!!) {
                if (!meal.name!!.startsWith(queryString!!, true))
                    continue

//                Timber.e("Meal: " + meal.name + " " + meal.getIngredients());

                val strCategoryExists = meal.javaClass.declaredFields.any { it.name == "strCategory" }
                val strTagsExists = meal.javaClass.declaredFields.any { it.name == "strTags" }

                if(parameter != null && strCategoryExists) {
                    when (parameter) {
                        is Category -> {
                            if (meal.strCategory != (parameter as Category).strCategory)
                                continue
                        }

                        is Area -> {
                            if (meal.strArea != (parameter as Area).strArea)
                                continue
                        }

                        is Ingredient -> {
                            if (!meal.ingredients.containsKey((parameter!! as Ingredient).strIngredient))
                                continue
                        }
                    }
                }
                if(strTagsExists && tagQuery != null && tagQuery != " ") {
                    if(!meal.strTags!!.contains(tagQuery!!, true))
                        continue
                }
                filteredMeals.add(meal)
            }
        } else {
            filteredMeals.addAll(fullMealsState.value!!)
        }
        setMealList(filteredMeals)
    }

    override fun updateSearchQuery(query: String) {

        Timber.e("updateSearchQuery " + query)

        var changedString = false

        if (query != queryString) {
            queryString = query
            changedString = true
        }

        val updatedFirstLetter = setQueryChar(query)
        Timber.e("timber " + queryChar + " " + queryString + " " + parameter)


        if(updatedFirstLetter)
            fetchMeals()

        else if(changedString && query != null && query.isNotEmpty())
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

    override fun setFilter(parameter: Parameter?) {
        this.parameter = parameter

        if(queryChar != null) {
            applyFilters()
        }
        else
            fetchMealsByParameter()
    }

    override fun setTag(tag: String) {
        this.tagQuery = tag
        applyFilters()
    }

    override fun setSort(sortType: SortType) {
        this.sortParameter = sortType
        applyFilters()
    }

    private fun setMealList(unsortedList: List<MealSimple>) {
        var meals : List<MealSimple>  = unsortedList

        if(sortParameter != null) {
            when(sortParameter) {
                SortType.NONE -> {}
                SortType.ABC -> {
                    meals = meals.sortedBy { it.name }
                }
                SortType.CALORIES -> {
                    //TODO sort by calories
                }
                else -> {}
            }
        }
        mealsState.value = MealsApiState.Success(meals)
    }
}