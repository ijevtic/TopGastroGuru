package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.models.responses.MealResponse
import com.example.topgastroguru.data.sources.local.MealDao
import com.example.topgastroguru.data.sources.remote.MealService
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class MealRepositoryImpl(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
) : MealRepository {

    override fun fetchMealById(id: String): Observable<MealResponse> {
//        Timber.e("1 Fetching meal with id: $id")
        return remoteDataSource.getMealById(id)
    }

    override fun insertMeal(meal: MealEntity): Completable {
        return localDataSource.insert(meal)
    }

    override fun deleteMealById(id: String) {
        return localDataSource.deleteById(id)
    }

    override fun deleteAndInsertMeal(id: String, meal: MealEntity) {
        localDataSource.deleteAndInsertMeal(id,meal)
    }
}