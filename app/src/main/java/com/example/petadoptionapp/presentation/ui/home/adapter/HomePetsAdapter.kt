package com.example.petadoptionapp.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.databinding.ItemPetCategoryBinding
import com.example.petadoptionapp.databinding.ItemPetsBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.ui.home.EPetGender
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
            bindItem(data)
        }

        private fun bindItem(data: AnimalResponse) {
            bindImage(data)
            bindPetAge(data)
            bindPetGender(data)
        }

        private fun bindImage(data: AnimalResponse){
            Glide.with(binding.ivPetImage.context).load(data.imageUrl).into(binding.ivPetImage)
        }

        private fun bindPetGender(data: AnimalResponse){
            if(data.gender == EPetGender.FEMALE){
                binding.ivPetGender.setImageResource(EPetGender.FEMALE.iconResource)
            }
            else
                binding.ivPetGender.setImageResource(EPetGender.MALE.iconResource)
        }

        private fun bindPetAge(data: AnimalResponse){
            val context = binding.ivPetGender.context
            val ageCategory = data.getAgeCategory(data)
            val displayName = ageCategory.getDisplayName(data.specie)
//            binding.ivPetGender. //TODO
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