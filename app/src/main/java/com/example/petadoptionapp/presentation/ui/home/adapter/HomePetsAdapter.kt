package com.example.petadoptionapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.petadoptionapp.databinding.ItemPetCategoryBinding
import com.example.petadoptionapp.databinding.ItemPetsBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder

typealias OnItemPetClickListener = (AnimalResponse) -> Unit
class HomePetsAdapter (
    diffutils: HomePetsDiffutils,
    private val onItemPetClickListener: OnItemPetClickListener
        ): ListAdapter<AnimalResponse, HomePetsAdapter.HomePetsViewHolder>(diffutils) {

    class HomePetsViewHolder(
        binding: ItemPetsBinding,
        private val onItemPetClickListener: OnItemPetClickListener
    ): ViewBindingViewHolder<ItemPetsBinding>(binding){

        fun bind(data: AnimalResponse){

        }

        private fun bindItem(data: AnimalResponse) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePetsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomePetsViewHolder(
            ItemPetsBinding.inflate(inflater, parent, false),
            onItemPetClickListener,
        )
    }

    override fun onBindViewHolder(holder: HomePetsViewHolder, position: Int) {
        val pet = getItem(position)
        holder.bind(pet)
    }
}