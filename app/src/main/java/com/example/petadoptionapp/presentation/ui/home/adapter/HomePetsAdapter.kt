package com.example.petadoptionapp.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetsBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener

typealias OnItemPetClickListener = (AnimalResponse) -> Unit

class HomePetsAdapter(
    diffutils: HomePetsDiffutils,
    private val onItemPetClickListener: OnItemPetClickListener
) : ListAdapter<AnimalResponse, HomePetsAdapter.HomePetsViewHolder>(diffutils) {

    class HomePetsViewHolder(
        binding: ItemPetsBinding,
        private val onItemPetClickListener: OnItemPetClickListener
    ) : ViewBindingViewHolder<ItemPetsBinding>(binding) {

        fun bind(data: AnimalResponse) {
            bindItem(data)
        }

        private fun bindItem(data: AnimalResponse) {
            bindImage(data)
            bindPetName(data)
            bindPetBreedAndAge(data)
            bindPetGender(data)
            bindItemClick(data)
        }

        private fun bindImage(data: AnimalResponse) {
            if (data.uploadedAssets.isNotEmpty()) {
                val imageUrl = data.uploadedAssets[0].path
                Glide.with(binding.ivPetImage.context).load(imageUrl).into(binding.ivPetImage)
            } else {
                Glide.with(binding.ivPetImage.context).load(Constants.PLACEHOLDER_PET_IMAGE)
                    .into(binding.ivPetImage)
            }
        }

        private fun bindPetName(data: AnimalResponse) {
            binding.tvPetName.text = data.name
        }

        private fun bindPetGender(data: AnimalResponse) {
            if (data.gender == EPetGender.FEMALE) {
                binding.ivPetGender.setImageResource(EPetGender.FEMALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_female_gender)
            } else {
                binding.ivPetGender.setImageResource(EPetGender.MALE.iconResource)
                binding.viewPet.setBackgroundResource(R.drawable.bg_male_gender)
            }
        }

        @SuppressLint("StringFormatMatches")
        private fun bindPetBreedAndAge(data: AnimalResponse) {
            val context = binding.ivPetGender.context
            val ageCategory = data.getAgeCategory(data)
            val displayName = ageCategory.getDisplayName(data.specie)
            val textToDisplay = context.getString(displayName)
            binding.tvPetBreed.text =
                context.getString(R.string.breed_age, data.breed, textToDisplay)
        }

        private fun bindItemClick(data: AnimalResponse) {
            binding.cvContainer.setOnDebounceClickListener {
                onItemPetClickListener.invoke(data)
            }
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