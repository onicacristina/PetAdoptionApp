package com.example.petadoptionapp.presentation.ui.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.network.models.Animal

class FavoritesDiffUtils : DiffUtil.ItemCallback<Animal>() {
    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
        return oldItem.isSaved == newItem.isSaved
    }
}