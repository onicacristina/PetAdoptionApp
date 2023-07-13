package com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.network.models.Hour

object SelectTimeDiffUtils : DiffUtil.ItemCallback<Hour>() {
    override fun areItemsTheSame(
        oldItem: Hour, newItem: Hour
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Hour, newItem: Hour
    ): Boolean {
        return oldItem.hour == newItem.hour
    }

}