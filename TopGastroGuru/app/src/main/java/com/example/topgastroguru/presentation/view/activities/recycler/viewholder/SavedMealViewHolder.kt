package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgastroguru.data.models.MealDto
import com.example.topgastroguru.databinding.LayoutItemSavedMealBinding
import timber.log.Timber
import java.io.File

class SavedMealViewHolder(private val itemBinding: LayoutItemSavedMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealDto) {
        itemBinding.titleTv.text = meal.name
//        itemBinding.caloriesTv.text = meal .toString()
//        Timber.e("link do slike:" + meal.link)

        if(meal.img!!.contains("storage")) {
            Glide.with(itemView)
                .load(File(meal.img))
                .into(itemBinding.imgView)
        }
        else {
            Glide.with(itemView) // Use itemView as the context
                .load(meal.img)
                .into(itemBinding.imgView)
        }
        itemBinding.deleteBtn.setOnClickListener {
            Timber.e("Delete button clicked")
        }
    }
    fun bindDeleteButton(meal: MealDto, onDeleteClick: (MealDto) -> Unit) {
        itemBinding.deleteBtn.setOnClickListener {
            onDeleteClick(meal)
        }
    }
}