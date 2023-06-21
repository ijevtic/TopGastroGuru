package com.example.topgastroguru.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.topgastroguru.R

class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setTheme(org.koin.android.R.style.Theme_AppCompat)
            setContentView(R.layout.activity_main)



        }


}

