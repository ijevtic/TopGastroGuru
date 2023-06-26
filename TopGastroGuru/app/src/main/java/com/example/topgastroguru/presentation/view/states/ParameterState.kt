package com.example.topgastroguru.presentation.view.states

import com.example.topgastroguru.data.models.Parameter


sealed class ParameterState {
    object Empty: ParameterState()

    data class Selected(val parameter: Parameter): ParameterState() {
        override fun toString(): String {
            return parameter.toString()
        }
    }
}