package com.example.topgastroguru.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.topgastroguru.R
import com.example.topgastroguru.data.repositories.MealRepositoryImpl
import com.example.topgastroguru.data.sources.local.MealDao
import com.example.topgastroguru.data.sources.remote.MealService
import com.example.topgastroguru.databinding.ActivityMainBinding
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import com.example.topgastroguru.presentation.view.activities.fragments.*
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedlViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val mealDetailedVM: MealDetaildContract.ViewModel by viewModel<MealDetailedlViewModel>()

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
            Timber.e("MainActivity notified of meal change")
            addFragment(MealDetailedFragment())
        })
    }

    fun addFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.fragmentContainer.id, fragment!!).addToBackStack(null)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

}

