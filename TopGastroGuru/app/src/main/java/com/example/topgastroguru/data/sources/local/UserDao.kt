package com.example.topgastroguru.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.topgastroguru.data.models.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class UserDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: UserEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<UserEntity>): Completable

    @Query("SELECT * FROM users")
    abstract fun getAll(): Observable<List<UserEntity>>

    @Query("DELETE FROM users")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<UserEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM users WHERE username LIKE :username || '%'")
    abstract fun getByUsername(username: String): Observable<List<UserEntity>>

}