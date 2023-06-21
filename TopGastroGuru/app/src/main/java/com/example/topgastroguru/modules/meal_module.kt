package com.example.topgastroguru.modules

import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.repositories.MealRepositoryImpl
import com.example.topgastroguru.data.sources.local.MealDataBase
import com.example.topgastroguru.data.sources.remote.MealService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealModule = module {

//    viewModel { MainViewModel(movieRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localDataSource = get()) }

    single { get<MealDataBase>().getUserDao() }

    single<MealService> { create(retrofit = get()) }

}