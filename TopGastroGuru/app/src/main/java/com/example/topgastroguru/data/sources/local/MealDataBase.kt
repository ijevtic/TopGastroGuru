package com.example.topgastroguru.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.topgastroguru.data.models.entities.MealEntity
import com.example.topgastroguru.data.models.entities.UserEntity
import com.example.topgastroguru.data.sources.local.converters.DateConverter
import com.example.topgastroguru.data.sources.local.converters.MapConverter

@Database(
    entities = [UserEntity::class,
                MealEntity::class],
    version = 3,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MealDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    // TODO figure out what does this do
    abstract fun getMealDao(): MealDao
}