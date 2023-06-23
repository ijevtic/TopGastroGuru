package com.example.topgastroguru.presentation.contract

import io.reactivex.Observable
import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.User
import com.example.topgastroguru.presentation.view.states.AddUserState
import com.example.topgastroguru.presentation.view.states.CheckCredentialsState
import com.example.topgastroguru.presentation.view.states.UsersState

class UserContract {
    interface ViewModel {
        val usersState: LiveData<UsersState>
        val addDone: LiveData<AddUserState>
        val checkCredentialsState: LiveData<CheckCredentialsState>
        fun getAllUsers()
        fun checkCredentials(email: String, password: String)
        fun addUser(user: User)
    }
}