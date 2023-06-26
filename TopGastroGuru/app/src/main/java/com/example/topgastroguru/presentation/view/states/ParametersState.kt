package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.Parameter

sealed class ParametersState {
    
    data class Success(val parameters: List<Parameter>): ParametersState()

    data class Error(val message: String): ParametersState()

    object Loading: ParametersState()
}