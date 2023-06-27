package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.models.responses.Food
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.sources.remote.CalorieService
import com.example.topgastroguru.data.sources.remote.converters.MealDetailedConverter
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MealDetailedViewModel(
    private val mealRepository: MealRepository,
    private val calorieService: CalorieService
): ViewModel(), MealDetaildContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val meal: MutableLiveData<MealDetailed> = MutableLiveData()

    //For remote data source
    // Used for testing of calorie service for now
    override fun fetchMealById(id: String) {
        val subscription = mealRepository
            .fetchMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meal.value = MealDetailedConverter.convertToMealDetailed(it.meals[0])

                    for ((key, value) in meal.value!!.ingredients.orEmpty()) {
                        if (key == null || value == null) {
                            break
                        }
                        Timber.e("Map iteration: Ingredient: $key, Measure: $value")

                        val subscription= calorieService
                            .getNutritionContent("$value $key")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                {
                                    Timber.e(it.toString())
                                    for (food in it) {
                                        meal.value!!.increaseCalValue(food.calories)
                                    }
                                },
                                {
                                    Timber.e(it)
                                }
                            )
                        subscriptions.add(subscription)
                    }
//                    Thread.sleep(5000)
                    Timber.e("Meal:\n" + meal.value.toString())
                },
                {
                    Timber.e(it)
                }
            )

        subscriptions.add(subscription)
    }


    //For local data source
    override fun getMealById(id: String) {
        val subscription = mealRepository
            .getMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meal.value = MealDetailed(
                        it.id,
                        it.name,
                        it.category,
                        it.date.toString(),
                        it.instructions,
                        it.img,
                        it.type,
                        it.link,
                        null)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun saveMealToDB(meal: MealEntity) {
        val subscription = mealRepository
            .insertMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal inserted")
                },
                {
                    Timber.e("error inserting meal")
                }
            )
        subscriptions.add(subscription)
    }

    fun getMealDetailed(): MealDetailed? {
        return meal.value
    }

    fun setMealDetailed(mealDetailed: MealDetailed) {
        meal.value = mealDetailed
    }

//    override fun fetchAll(): Observable<Resource<Unit>> {
//        return remoteDataSource
//            .getAll()
//            .doOnNext {
//                Timber.e("Upis u bazu")
//                val entities = it.map {
//                    MovieEntity(
//                        it.id,
//                        it.title,
//                        it.description
//                    )
//                }
//                localDataSource.deleteAndInsertAll(entities)
//            }
//            .map {
//                Resource.Success(Unit)
//            }
//    }
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}