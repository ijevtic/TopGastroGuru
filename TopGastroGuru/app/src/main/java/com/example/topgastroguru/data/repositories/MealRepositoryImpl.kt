package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.sources.local.MealDao
import io.reactivex.Completable

class MealRepositoryImpl(
    private val localDataSource: MealDao
) : MealRepository {
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