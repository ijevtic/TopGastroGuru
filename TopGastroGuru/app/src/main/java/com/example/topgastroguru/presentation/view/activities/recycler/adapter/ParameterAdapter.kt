package com.example.topgastroguru.presentation.view.activities.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.topgastroguru.data.models.Parameter
import com.example.topgastroguru.databinding.LayoutItemParameterBinding
import com.example.topgastroguru.presentation.view.activities.recycler.diff.ParameterDiffCallback
import com.example.topgastroguru.presentation.view.activities.recycler.viewholder.ParameterViewHolder

class ParameterAdapter(private val onItemClick: (Parameter) -> Unit) : ListAdapter<Parameter, ParameterViewHolder>(ParameterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        val itemBinding = LayoutItemParameterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParameterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        val parameter = getItem(position)
        holder.bind(parameter)

        holder.itemView.setOnClickListener {
            onItemClick(parameter)
        }
    }

}