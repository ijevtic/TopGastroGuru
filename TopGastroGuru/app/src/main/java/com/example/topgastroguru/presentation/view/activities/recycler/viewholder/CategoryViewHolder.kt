package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.databinding.LayoutItemCategoryBinding

class CategoryViewHolder (private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: Category) {
        itemBinding.titleTv.text = category.strCategory
//        itemBinding.titleTv.setOnClickListener {
//            Toast.makeText(itemBinding.root.context, "Clicked on ${meal.name}", Toast.LENGTH_SHORT).show()
//        }
    }

}