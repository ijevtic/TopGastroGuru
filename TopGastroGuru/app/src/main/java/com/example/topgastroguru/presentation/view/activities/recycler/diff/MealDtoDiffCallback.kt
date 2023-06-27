package com.example.topgastroguru.presentation.view.activities.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.topgastroguru.data.models.MealDto

class MealDtoDiffCallback : DiffUtil.ItemCallback<MealDto>() {

    override fun areItemsTheSame(oldItem: MealDto, newItem: MealDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealDto, newItem: MealDto): Boolean {
        return oldItem.name == newItem.name/* && oldItem.link == newItem.link*/
    }

}