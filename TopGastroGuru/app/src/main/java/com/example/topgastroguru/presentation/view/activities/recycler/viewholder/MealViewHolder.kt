package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.topgastroguru.data.models.MealSimple
import com.example.topgastroguru.databinding.LayoutItemMealBinding

class MealViewHolder(private val itemBinding: LayoutItemMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealSimple) {
        itemBinding.titleTv.text = meal.name

//        itemBinding.titleTv.setOnClickListener {
//            Toast.makeText(itemBinding.root.context, "Clicked on ${meal.name}", Toast.LENGTH_SHORT).show()
//        }
    }

}