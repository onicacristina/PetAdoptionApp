package com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemSelectHourBinding
import com.example.petadoptionapp.network.models.Hour
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (Hour) -> Unit

class SelectTimeAdapter(
    diffUtils: SelectTimeDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Hour, SelectTimeAdapter.ViewHolder>(diffUtils) {

    class ViewHolder(
        binding: ItemSelectHourBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemSelectHourBinding>(binding) {

        fun bind(data: Hour) {
            bindItem(data)
        }

        private fun bindItem(data: Hour) {
            bindHour(data)
            bindClick(data)
        }

        private fun bindHour(data: Hour) {
            binding.tvTime.text = data.hour
        }

        private fun bindClick(data: Hour) {
            binding.container.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemSelectHourBinding.inflate(inflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }
}