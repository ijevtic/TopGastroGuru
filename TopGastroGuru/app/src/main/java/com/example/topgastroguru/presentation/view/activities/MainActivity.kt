package com.example.topgastroguru.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.data.repositories.MealRepositoryImpl
import com.example.topgastroguru.data.sources.local.MealDao
import com.example.topgastroguru.data.sources.remote.MealService
import com.example.topgastroguru.databinding.ActivityMainBinding
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import com.example.topgastroguru.presentation.view.activities.fragments.AllMealsFragment
import com.example.topgastroguru.presentation.view.activities.fragments.MealPlanFragment
import com.example.topgastroguru.presentation.view.activities.fragments.MyMealsFragment
import com.example.topgastroguru.presentation.view.activities.fragments.ProfileFragment
import com.example.topgastroguru.presentation.view.activities.fragments.StatisticsFragment
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedlViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mealDetailedVM: MealDetaildContract.ViewModel by viewModel<MealDetailedlViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(org.koin.android.R.style.Theme_AppCompat)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.navigation_1 -> AllMealsFragment()
                R.id.navigation_2 -> MyMealsFragment()
                R.id.navigation_3 -> MealPlanFragment()
                R.id.navigation_4 -> StatisticsFragment()
                R.id.navigation_5 -> ProfileFragment()
                // Add more cases for additional fragments
                else -> AllMealsFragment() // Default fragment
            }
            replaceFragment(fragment)
            true
        }
        // Set the initial fragment
        replaceFragment(AllMealsFragment())
//        val mealDetailedVM= MealDetailedlViewModel(MealRepositoryImpl(MealDao(),MealService())
        mealDetailedVM.fetchMealById("52772")
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

}

