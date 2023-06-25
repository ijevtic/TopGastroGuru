package com.example.topgastroguru.presentation.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topgastroguru.data.repositories.CategoryRepository
import com.example.topgastroguru.data.sources.remote.converters.CategoryConverter
import com.example.topgastroguru.presentation.contract.CategoriesContract
import com.example.topgastroguru.presentation.view.states.CategoriesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CategoriesContract.ViewModel {
    override val categoriesState: MutableLiveData<CategoriesState> = MutableLiveData()

    private val subscriptions = CompositeDisposable()

    override fun fetchCategories() {
        val subscription = categoryRepository
            .fetchAll()
            .map<CategoriesState> { CategoriesState.Success(CategoryConverter.mapCategoryResponseToCategory(it)) }
            .startWith(CategoriesState.Loading)
            .onErrorReturn { CategoriesState.Error(it.message ?: "Unknown error occurred") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Timber.e("Response categories" + it.toString());
                when (it) {
                    is CategoriesState.Success -> {
                        categoriesState.value = it
                    }
                    is CategoriesState.Loading -> {
                        categoriesState.value = CategoriesState.Loading
                    }
                    is CategoriesState.Error -> {
                        categoriesState.value = CategoriesState.Error(it.message)
                    }
                }
            }
        subscriptions.add(subscription)
    }
}
