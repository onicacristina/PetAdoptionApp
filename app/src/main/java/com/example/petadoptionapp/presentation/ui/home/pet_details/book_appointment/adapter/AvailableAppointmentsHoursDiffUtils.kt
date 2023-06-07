package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.model.AvailableHour

class AvailableAppointmentsHoursDiffUtils : DiffUtil.ItemCallback<AvailableHour>() {
    override fun areItemsTheSame(oldItem: AvailableHour, newItem: AvailableHour): Boolean {
        return oldItem.hour == newItem.hour
    }

    override fun areContentsTheSame(oldItem: AvailableHour, newItem: AvailableHour): Boolean {
        return oldItem.isSelected == newItem.isSelected
    }
}