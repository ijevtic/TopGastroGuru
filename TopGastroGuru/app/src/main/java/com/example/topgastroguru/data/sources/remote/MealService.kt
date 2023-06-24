package com.example.topgastroguru.data.sources.remote

import com.example.topgastroguru.data.models.MealDetailed
import com.example.topgastroguru.data.models.responses.MealResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET("meals")
    fun getAll(@Query("limit") limit: Int = 1000): Observable<List<MealResponse>>

    @GET("lookup.php?")
    fun getMealById(@Query("i") mealId: String): Observable<MealResponse>

}