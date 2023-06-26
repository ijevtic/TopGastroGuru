package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.databinding.LayoutItemSavedMealBinding

class SavedMealViewHolder(private val itemBinding: LayoutItemSavedMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealSimple) {
        itemBinding.titleTv.text = meal.name

//        Glide.with(itemView) // Use itemView as the context
//            .load(meal.link)
//            .into(itemBinding.imgView)

//        itemBinding.titleTv.setOnClickListener {
//            Toast.makeText(itemBinding.root.context, "Clicked on ${meal.name}", Toast.LENGTH_SHORT).show()
//        }
    }

}