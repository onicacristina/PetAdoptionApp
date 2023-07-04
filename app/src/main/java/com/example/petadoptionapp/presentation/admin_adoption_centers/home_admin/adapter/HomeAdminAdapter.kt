package com.example.petadoptionapp.presentation.admin_adoption_centers.home_admin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.ItemPetAdminBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsDiffutils
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.ViewBindingViewHolder
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener

typealias OnItemPetClickListener = (AnimalResponse) -> Unit
typealias OnEditClickListener = (AnimalResponse) -> Unit
typealias OnDeleteClickListener = (AnimalResponse) -> Unit

class HomeAdminAdapter(
    diffutils: HomePetsDiffutils,
    private val onItemPetClickListener: OnItemPetClickListener,
    private val onEditClickListener: OnEditClickListener,
    private val onDeleteClickListener: OnDeleteClickListener,
) : ListAdapter<AnimalResponse, HomeAdminAdapter.HomePetsViewHolder>(diffutils) {

    class HomePetsViewHolder(
        binding: ItemPetAdminBinding,
        private val onItemPetClickListener: OnItemPetClickListener,
        private val onEditClickListener: OnEditClickListener,
        private val onDeleteClickListener: OnDeleteClickListener,
    ) : ViewBindingViewHolder<ItemPetAdminBinding>(binding) {

        fun bind(data: AnimalResponse) {
            bindItem(data)
        }

        private fun bindItem(data: AnimalResponse) {
            bindImage(data)
            bindPetName(data)
            bindPetBreedAndAge(data)
            bindPetGender(data)
            bindAddedAt(data)
            bindItemClick(data)
            bindEditClick(data)
            bindDeleteClick(data)
        }

        private fun bindAddedAt(data: AnimalResponse) {
            val context = binding.tvAddedAt.context
            val addedAt = data.getFormattedDate(LocaleHelper.getLocale().locale, data.createdAt)
            binding.tvAddedAt.text = context.getString(R.string.added_at, addedAt)
        }

//        private fun bindImage(data: AnimalResponse) {
//            Glide.with(binding.ivPet.context).load(data.imageUrl).into(binding.ivPet)
//        }

        private fun bindImage(data: AnimalResponse) {
            if (data.uploadedAssets.isNotEmpty()) {
                val imageUrl = data.uploadedAssets[0].path
                Glide.with(binding.ivPet.context).load(imageUrl).into(binding.ivPet)
            } else {
                // Logic pentru încărcarea unei imagini alternative sau afișarea unui placeholder
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

        private fun bindDeleteClick(data: AnimalResponse) {
            binding.btnDelete.setOnDebounceClickListener {
                onDeleteClickListener.invoke(data)
            }
        }

        private fun bindEditClick(data: AnimalResponse) {
            binding.btnEdit.setOnDebounceClickListener {
                onEditClickListener.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePetsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomePetsViewHolder(
            ItemPetAdminBinding.inflate(inflater, parent, false),
            onItemPetClickListener,
            onEditClickListener,
            onDeleteClickListener
        )
    }

    override fun onBindViewHolder(holder: HomePetsViewHolder, position: Int) {
        val pet = getItem(position)
        holder.bind(pet)
    }
}