package com.example.topgastroguru.data.models

import com.example.topgastroguru.util.ParameterType

interface Parameter{
    val type: ParameterType
    override fun toString(): String
    fun areItemsTheSame(newItem: Parameter): Boolean
    fun areContentsTheSame(newItem: Parameter): Boolean
}