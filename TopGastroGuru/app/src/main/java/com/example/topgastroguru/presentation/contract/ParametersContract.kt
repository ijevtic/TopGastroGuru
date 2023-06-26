package com.example.topgastroguru.presentation.contract

import androidx.lifecycle.LiveData
import com.example.topgastroguru.presentation.view.states.ParameterState
import com.example.topgastroguru.presentation.view.states.ParametersState
import com.example.topgastroguru.util.ParameterType

interface ParametersContract {
    interface ViewModel {

        val parametersState: LiveData<ParametersState>
        val selectedParameterState: LiveData<ParameterState>
        fun fetchAll(type: ParameterType)

    }
}