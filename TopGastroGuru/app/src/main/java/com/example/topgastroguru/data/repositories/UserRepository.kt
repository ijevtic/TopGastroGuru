package com.example.topgastroguru.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import com.example.topgastroguru.data.models.User

interface UserRepository {

    fun getAll(): Observable<List<User>>
    fun getAllByEmail(email: String): Observable<List<User>>
    fun insert(user: User): Completable
}