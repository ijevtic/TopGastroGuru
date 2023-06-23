package com.example.topgastroguru.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.view.activities.fragments.MealDetailedFragment

class MainActivity : AppCompatActivity() {
//  true to skip the login for testing purpouse
    private var isLoggedIn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(org.koin.android.R.style.Theme_AppCompat)



        setContentView(R.layout.activity_main)
        setContentView(R.layout.fragment_main)

//        setFragment()

    }

//  this function will be in onClickListener for RecyclerView of meals
    private fun setMealDetaild(){

    }

    fun addFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentMain, fragment!!).addToBackStack(null)
        transaction.commit()
    }

    private fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (isLoggedIn) {
            transaction.add(R.id.fragmentMain, MealDetailedFragment())
        } else {
//            transaction.add(R.id.fragmentMain, LoginFragment())
        }
        transaction.commit()
    }

}

