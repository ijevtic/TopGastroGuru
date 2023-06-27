package com.example.topgastroguru.presentation.view.activities.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.topgastroguru.data.models.MealDto
import com.example.topgastroguru.databinding.LayoutItemSavedMealBinding
import com.example.topgastroguru.presentation.view.activities.recycler.diff.MealDtoDiffCallback
import com.example.topgastroguru.presentation.view.activities.recycler.viewholder.SavedMealViewHolder

class SavedMealAdapter(private val onItemClick: (MealDto) -> Unit, private val onDeleteClick: (MealDto) -> Unit) : ListAdapter<MealDto, SavedMealViewHolder>(
    MealDtoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealViewHolder {
        val itemBinding = LayoutItemSavedMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedMealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SavedMealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)

        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }

        holder.bindDeleteButton(meal, onDeleteClick)
    }

}