package com.example.topgastroguru.presentation.view.activities.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.databinding.LayoutItemCategoryBinding
import com.example.topgastroguru.presentation.view.activities.recycler.diff.CategoryDiffCallback
import com.example.topgastroguru.presentation.view.activities.recycler.viewholder.CategoryViewHolder

class CategoryAdapter : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}