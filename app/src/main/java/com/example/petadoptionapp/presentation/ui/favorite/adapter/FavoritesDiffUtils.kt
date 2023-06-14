package com.example.petadoptionapp.presentation.ui.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.petadoptionapp.network.models.response.AnimalResponse

class FavoritesDiffUtils: DiffUtil.ItemCallback<AnimalResponse>(){
    override fun areItemsTheSame(oldItem: AnimalResponse, newItem: AnimalResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnimalResponse, newItem: AnimalResponse): Boolean {
        return oldItem.isSaved == newItem.isSaved
    }
}