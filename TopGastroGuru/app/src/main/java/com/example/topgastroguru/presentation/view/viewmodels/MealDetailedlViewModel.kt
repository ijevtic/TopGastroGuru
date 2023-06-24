package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import com.example.topgastroguru.presentation.view.states.CheckCredentialsState
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MealDetailedlViewModel(
    private val mealRepository: MealRepository
): ViewModel(), MealDetaildContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val meal: MutableLiveData<MealDetailed> = MutableLiveData()

    override fun fetchMealById(id: String) {
//        Timber.e("Fetching meal with id: $id")
        val subscription = mealRepository
            .fetchMealById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Meal fetched " + it.meals[0])
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)

//        Timber.e(subscription.get)

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
}