package com.example.topgastroguru.data.sources.remote

import com.example.topgastroguru.data.models.responses.CategoryResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ParametersService {

    @GET("list.php?c=list")
    fun getAllCategories(): Observable<CategoryResponse>

//    @GET("list.php?a=list")
//    fun getAllCategories(): Observable<AreaResponse>

//    @GET("list.php?i=list")
//    fun getAllCategories(): Observable<IngredientResponse>

}