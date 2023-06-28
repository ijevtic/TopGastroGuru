package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.databinding.LayoutItemParameterBinding

class ParameterViewHolder (private val itemBinding: LayoutItemParameterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(parameter: Parameter) {
        itemBinding.titleTv.text = parameter.toString()
        if(parameter is Category) {
            Glide.with(itemView) // Use itemView as the context
                .load((parameter as Category).strCategoryThumb)
                .into(itemBinding.imgView)
        }
    }

}