package com.example.topgastroguru.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.topgastroguru.databinding.ActivityMainBinding
import com.example.topgastroguru.presentation.contract.ParametersContract
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.fragments.MainFragment
import com.example.topgastroguru.presentation.view.viewmodels.AllMealsViewModel
import com.example.topgastroguru.presentation.view.viewmodels.ParameterViewModel
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.example.topgastroguru.presentation.view.fragments.MealDetailedFragment

class MainActivity : AppCompatActivity() {

    private val mealDetailedVM: MealDetaildContract.ViewModel by viewModel<MealDetailedViewModel>()
    private val mealsViewModel: MealsContract.ViewModel by viewModel<AllMealsViewModel>()
    private val parameterViewModel: ParametersContract.ViewModel by viewModel<ParameterViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(org.koin.android.R.style.Theme_AppCompat)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init () {
        // Set the initial fragment
        replaceFragment(MainFragment())
        initObservers()
//        mealDetailedVM.fetchMealById("52772")
    }

    private fun initObservers(){
        mealDetailedVM.meal.observe(this, Observer {
//            Timber.e("MainActivity notified of meal change")
            addFragment(MealDetailedFragment())
        })
    }

    fun addFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.fragmentContainer.id, fragment!!).addToBackStack(null)
        transaction.commit()
    }

    fun addFragmentHide(fragment: Fragment?) {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        val transaction = supportFragmentManager.beginTransaction()

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        transaction.add(binding.fragmentContainer.id, fragment!!).addToBackStack(null)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

}

