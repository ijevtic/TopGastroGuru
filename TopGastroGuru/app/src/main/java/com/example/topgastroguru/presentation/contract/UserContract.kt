package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.data.models.User
import com.example.topgastroguru.presentation.view.states.AddUserState
import com.example.topgastroguru.presentation.view.states.UsersState

class UserContract {
    interface ViewModel {
        val usersState: LiveData<UsersState>
        val addDone: LiveData<AddUserState>
        fun getAllUsers()
        fun getAllUsersByEmail(email: String)
        fun addUser(user: User)
    }
}