package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.entities.MealEntity
import io.reactivex.Completable

interface MealRepository {
    // TODO
    fun insertMeal(meal: MealEntity): Completable
    fun deleteMealById(id:String)
    fun deleteAndInsertMeal(id: String, meal: MealEntity)

//    fun fetchAll(): Observable<Resource<Unit>>
//    fun getAll(): Observable<List<Movie>>
//    fun getAllByName(name: String): Observable<List<Movie>>
//    fun insert(movie: Movie): Completable

}