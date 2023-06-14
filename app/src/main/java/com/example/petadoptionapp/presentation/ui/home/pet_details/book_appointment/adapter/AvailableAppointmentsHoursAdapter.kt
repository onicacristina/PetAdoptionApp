package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemAvailableTimeAppointmentBinding
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.model.AvailableHour
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemClickListener = (AvailableHour) -> Unit

class AvailableAppointmentsHoursAdapter(
    diffUtils: AvailableAppointmentsHoursDiffUtils,
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<AvailableHour, AvailableAppointmentsHoursAdapter.AvailableHoursViewHolder>(diffUtils) {

    class AvailableHoursViewHolder(
        binding: ItemAvailableTimeAppointmentBinding,
        private val onItemClickListener: OnItemClickListener
    ) : ViewBindingViewHolder<ItemAvailableTimeAppointmentBinding>(binding) {

        fun bind(data: AvailableHour) {
            bindItem(data)
        }

        private fun bindItem(data: AvailableHour) {
            bindHour(data)
            bindClick(data)
        }

        private fun bindHour(data: AvailableHour) {
            binding.tvTime.text = data.hour
            if (data.isSelected) {
                binding.tvTime.setBackgroundResource(R.drawable.bg_item_pet_category_yellow)
            } else
                binding.tvTime.setBackgroundResource(R.drawable.bg_item_pet_category_gray)
        }

        private fun bindClick(data: AvailableHour) {
            binding.container.setOnClickListener {
                onItemClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableHoursViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AvailableHoursViewHolder(
            ItemAvailableTimeAppointmentBinding.inflate(inflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: AvailableHoursViewHolder, position: Int) {
        val hour = getItem(position)
        holder.bind(hour)
    }
}