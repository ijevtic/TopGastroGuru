package com.example.topgastroguru.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.topgastroguru.data.models.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}