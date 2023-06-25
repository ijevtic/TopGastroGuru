package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.responses.CategoryResponse
import io.reactivex.Observable

interface CategoryRepository {
    fun fetchAll(): Observable<CategoryResponse>
}