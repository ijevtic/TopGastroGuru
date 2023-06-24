package com.example.topgastroguru.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.topgastroguru.R
import com.example.topgastroguru.data.models.User
import com.example.topgastroguru.databinding.ActivityLoginPageBinding
import com.example.topgastroguru.presentation.contract.UserContract
import com.example.topgastroguru.presentation.view.states.CheckCredentialsState
import com.example.topgastroguru.presentation.view.states.UsersState
import com.example.topgastroguru.presentation.view.viewmodels.LoginViewModel
import com.example.topgastroguru.util.Constants
import com.example.topgastroguru.util.Util
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ActivityLoginPage : AppCompatActivity() {


    private val mainViewModel: UserContract.ViewModel by viewModel<LoginViewModel>()

    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(org.koin.android.R.style.Theme_AppCompat)

        installSplashScreen()

        // check if the user is already logged in
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if (sharedPreferences.contains(Constants.IS_LOGGED_IN) && sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, true)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding = ActivityLoginPageBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        init()
    }

    private fun init() {
        initUi()
        initObservers()
//        val userToAdd = User(
//            id = "2",
//            email = "sejo@kalac.com",
//            username = "muuu",
//            password = "a jesam te volio",
//            fullName = "bolje mi je sad bez tebe"
//        )
//        mainViewModel.addUser(userToAdd)
//        mainViewModel.getAllUsers()
    }

    private fun initUi() {
        binding.btnLogin.setOnClickListener {

            val emailText : String = binding.email.text.toString()
            val usernameText : String = binding.username.text.toString()
            val passwordText : String = binding.password.text.toString()
            var errorMessage : String = "Invalid input:\n"

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
                mainViewModel.checkCredentials(emailText, passwordText)
            }
        }
    }

    private fun initObservers() {
        mainViewModel.addDone.observe(this, Observer {
            Timber.e("renderState: $it")
            Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
        })

        mainViewModel.usersState.observe(this, Observer {
            Timber.e("renderState: $it")
            when (it) {
                is UsersState.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val users = it.users
                    if (users.isEmpty()) {
                        Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
                    }
                    for (user in users) {
                    }
                }
                is UsersState.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is UsersState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })

        mainViewModel.checkCredentialsState.observe(this, Observer {
            Timber.e("renderState: $it")
            when (it) {
                is CheckCredentialsState.Success -> {
                    val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean(Constants.IS_LOGGED_IN, true)
                    editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is CheckCredentialsState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is CheckCredentialsState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}