package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.User

sealed class CheckCredentialsState {
    object Loading: CheckCredentialsState()
    object Success: CheckCredentialsState()
    data class Error(val message: String): CheckCredentialsState()
}