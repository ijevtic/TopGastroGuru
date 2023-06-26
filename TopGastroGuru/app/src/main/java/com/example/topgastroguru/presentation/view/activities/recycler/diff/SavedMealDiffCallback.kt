package com.example.topgastroguru.presentation.view.activities.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.topgastroguru.data.models.MealSimple

class SavedMealDiffCallback : DiffUtil.ItemCallback<MealSimple>() {

    override fun areItemsTheSame(oldItem: MealSimple, newItem: MealSimple): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealSimple, newItem: MealSimple): Boolean {
        return oldItem.name == newItem.name/* && oldItem.link == newItem.link*/
    }

}