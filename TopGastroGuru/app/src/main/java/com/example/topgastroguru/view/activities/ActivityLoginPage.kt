package com.example.topgastroguru.view.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.topgastroguru.R
import com.example.topgastroguru.util.Constants

class ActivityLoginPage : AppCompatActivity() {

//    private val splashScreenViewModel: SplashScreenViewModel? = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the splash screen transition.
//        val splashScreen = SplashScreen.installSplashScreen(this)
//        splashScreen.setKeepOnScreenCondition {
//            try {
//                Thread.sleep(500)
//            } catch (e: InterruptedException) {
//                throw RuntimeException(e)
//            }
//            false
//        }

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if (sharedPreferences.contains(Constants.IS_LOGGED_IN) && sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, true)) {

//            val intent = Intent(this, HomePage::class.java)
//            startActivity(intent)


        }

        setContentView(R.layout.activity_login_page)
    }
}