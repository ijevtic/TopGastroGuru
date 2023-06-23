package com.example.topgastroguru.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.ActivityLoginPageBinding
import com.example.topgastroguru.util.Constants
import com.example.topgastroguru.util.Util

class ActivityLoginPage : AppCompatActivity() {


    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(org.koin.android.R.style.Theme_AppCompat)

        installSplashScreen()

        binding = ActivityLoginPageBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if (sharedPreferences.contains(Constants.IS_LOGGED_IN) && sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, true)) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.btnLogin.setOnClickListener {

            val emailText : String = binding.email.text.toString()
            val usernameText : String = binding.username.text.toString()
            val passwordText : String = binding.password.text.toString()
            var errorMessage : String = "Invalid input:\n";

            if (!Util.isValidEmail(emailText)) {
                errorMessage += """
                    ${getString(R.string.email_wrong_info)}
                    
                    """.trimIndent()
                binding.emailError.visibility = TextView.VISIBLE
            } else {
                binding.emailError.visibility = TextView.GONE
            }
            if (!Util.isValidUsername(usernameText)) {
                errorMessage += """
                    ${getString(R.string.username_wrong_info)}
                    
                    """.trimIndent()
                binding.usernameError.visibility = TextView.VISIBLE
            } else {
                binding.usernameError.visibility = TextView.GONE
            }
            if (!Util.isValidPassword(passwordText)) {
                errorMessage += """
                    ${getString(R.string.password_wrong_info)}
                    
                    """.trimIndent()
                binding.passwordError.visibility = TextView.VISIBLE
            } else {
                binding.passwordError.visibility = TextView.GONE
            }
            if (errorMessage != "Invalid input:\n") {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                //check db for user

                val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean(Constants.IS_LOGGED_IN, true)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}