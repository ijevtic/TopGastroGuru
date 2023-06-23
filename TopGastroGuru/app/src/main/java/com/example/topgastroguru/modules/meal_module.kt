package com.example.topgastroguru.modules

import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.repositories.MealRepositoryImpl
import com.example.topgastroguru.data.repositories.UserRepository
import com.example.topgastroguru.data.repositories.UserRepositoryImpl
import com.example.topgastroguru.data.sources.local.MealDataBase
import com.example.topgastroguru.data.sources.remote.MealService
import com.example.topgastroguru.presentation.view.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealModule = module {

    viewModel { LoginViewModel(userRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localDataSource = get()) }

    single<UserRepository> { UserRepositoryImpl(localDataSource = get()) }

    single { get<MealDataBase>().getUserDao() }

    single<MealService> { create(retrofit = get()) }

}