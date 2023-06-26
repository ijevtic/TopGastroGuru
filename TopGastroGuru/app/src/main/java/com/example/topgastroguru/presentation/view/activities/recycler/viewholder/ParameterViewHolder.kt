package com.example.topgastroguru.presentation.view.activities.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.topgastroguru.data.models.Category
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.databinding.LayoutItemParameterBinding

class ParameterViewHolder (private val itemBinding: LayoutItemParameterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(parameter: Parameter) {
        itemBinding.titleTv.text = parameter.toString()
    }

}