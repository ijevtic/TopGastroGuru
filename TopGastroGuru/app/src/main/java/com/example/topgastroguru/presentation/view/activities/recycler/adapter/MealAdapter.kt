package com.example.topgastroguru.presentation.view.activities.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.databinding.LayoutItemMealBinding
import com.example.topgastroguru.presentation.view.activities.recycler.diff.MealDiffCallback
import com.example.topgastroguru.presentation.view.activities.recycler.viewholder.MealViewHolder

class MealAdapter(private val onItemClick: (MealSimple) -> Unit) : ListAdapter<MealSimple, MealViewHolder>(
    MealDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)

        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }
    }

}