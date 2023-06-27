package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.databinding.LayoutItemSavedMealBinding
import timber.log.Timber
import java.io.File

class SavedMealViewHolder(private val itemBinding: LayoutItemSavedMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealSimple) {
        itemBinding.titleTv.text = meal.name
        Timber.e("link do slike:" + meal.link)

        if(meal.link!!.contains("storage")) {
            Glide.with(itemView)
                .load(File(meal.link))
                .into(itemBinding.imgView)
        }
        else {
            Glide.with(itemView) // Use itemView as the context
                .load(meal.link)
                .into(itemBinding.imgView)
        }
    }

}