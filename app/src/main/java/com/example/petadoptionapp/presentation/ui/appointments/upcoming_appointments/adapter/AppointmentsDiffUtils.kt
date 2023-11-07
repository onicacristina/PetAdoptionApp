package com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.network.models.Booking

class AppointmentsDiffUtils : DiffUtil.ItemCallback<Booking>() {
    override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
        return oldItem.dateAndTime == newItem.dateAndTime
    }
}