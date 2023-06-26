package com.example.topgastroguru.data.repositories

import com.example.topgastroguru.data.models.responses.ParameterResponse
import com.example.topgastroguru.util.ParameterType
import io.reactivex.Observable

interface ParameterRepository {
    fun fetchAll(type: ParameterType): Observable<ParameterResponse>

}