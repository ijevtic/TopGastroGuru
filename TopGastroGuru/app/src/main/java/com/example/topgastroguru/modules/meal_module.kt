package com.example.topgastroguru.modules

import com.example.topgastroguru.data.repositories.ParameterRepository
import com.example.topgastroguru.data.repositories.ParameterRepositoryImpl
import com.example.topgastroguru.data.repositories.MealRepository
import com.example.topgastroguru.data.repositories.MealRepositoryImpl
import com.example.topgastroguru.data.repositories.UserRepository
import com.example.topgastroguru.data.repositories.UserRepositoryImpl
import com.example.topgastroguru.data.sources.local.MealDataBase
import com.example.topgastroguru.data.sources.remote.MealService
import com.example.topgastroguru.data.sources.remote.ParametersService
import com.example.topgastroguru.presentation.view.viewmodels.AllMealsViewModel
import com.example.topgastroguru.presentation.view.viewmodels.ParameterViewModel
import com.example.topgastroguru.presentation.view.viewmodels.LoginViewModel
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import com.example.topgastroguru.presentation.view.viewmodels.MealEntityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealModule = module {

    viewModel { LoginViewModel(userRepository = get()) }

    viewModel { MealDetailedViewModel(mealRepository = get()) }

    viewModel { MealEntityViewModel(mealRepository = get()) }

    viewModel { AllMealsViewModel(mealRepository = get()) }

    viewModel { ParameterViewModel(parameterRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localDataSource = get(), remoteDataSource = get ()) }

    single<UserRepository> { UserRepositoryImpl(localDataSource = get()) }

    single<ParameterRepository> { ParameterRepositoryImpl(remoteDataSource = get ()) }

    single { get<MealDataBase>().getUserDao() }

    single { get<MealDataBase>().getMealDao() }

    single<MealService> { create(retrofit = get()) }

    single<ParametersService> { create(retrofit = get()) }

}