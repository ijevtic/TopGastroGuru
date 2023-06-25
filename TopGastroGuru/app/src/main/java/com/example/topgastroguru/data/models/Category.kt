package com.example.topgastroguru.data.models

class Category(
    val strCategory: String?
){
    override fun toString(): String {
        return strCategory?:""
    }
}