package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.responses.CategoryResponse
import com.example.topgastroguru.data.sources.remote.ParametersService
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber

class CategoryRepositoryImpl(
    private val remoteDataSource: ParametersService
) : CategoryRepository {
    override fun fetchAll(): Observable<CategoryResponse> {
        return remoteDataSource.getAllCategories()
    }
}