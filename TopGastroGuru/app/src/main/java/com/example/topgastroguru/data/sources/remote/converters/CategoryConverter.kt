package com.example.topgastroguru.data.sources.remote.converters

import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.data.models.responses.CategoryResponse


class CategoryConverter {
    companion object {
        fun mapCategoryResponseToCategory(categoryResponse: CategoryResponse): List<Category> {
            val returnList: MutableList<Category> = mutableListOf()
            categoryResponse.meals.forEach {
                returnList.add(Category(it.strCategory))
            }
            return returnList
        }
    }
}